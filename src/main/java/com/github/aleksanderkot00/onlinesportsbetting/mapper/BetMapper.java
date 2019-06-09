package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
