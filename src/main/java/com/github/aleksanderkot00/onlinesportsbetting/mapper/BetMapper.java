package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BetMapper {

    private final EventService eventService;

    @Autowired
    public BetMapper(EventService eventService) {
        this.eventService = eventService;
    }

    public Bet mapToBet(BetDto betDto) {
        Event event = eventService.getEvent(betDto.getEventId());
        Bet bet = new Bet();
        bet.setEvent(event);
        bet.setType(betDto.getType());
        bet.setOdds(betDto.getOdds());
        bet.setActive(betDto.isActive());

        return bet;
    }

    public BetDto mapToBetDto(Bet bet) {
        return BetDto.builder()
                .betId(bet.getBetId())
                .eventId(bet.getEvent().getEventId())
                .isActive(bet.isActive())
                .odds(bet.getOdds())
                .type(bet.getType())
                .build();
    }

    public List<BetDto> mapToBetDtoList(List<Bet> bets) {
        return bets.stream()
                .map(this::mapToBetDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
