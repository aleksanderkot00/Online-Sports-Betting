package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.BetDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SlipMapper {

    private final BetMapper betMapper;

    @Autowired
    public SlipMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }

    public SlipDto mapToSlipDto(Slip slip) {
        Set<BetDto> betsDto = slip.getBets().stream()
                .map(betMapper::mapToBetDto)
                .collect(Collectors.toSet());
        return new SlipDto(betsDto, slip.getStake(), slip.getState(), slip.getTotalOdds());
    }
}
