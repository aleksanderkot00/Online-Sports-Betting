package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SlipMapper {

    private final BetMapper betMapper;

    @Autowired
    public SlipMapper(BetMapper betMapper) {
        this.betMapper = betMapper;
    }

    public SlipDto mapToSlipDto(Slip slip) {
        SlipDto slipDto = new SlipDto();
        slipDto.setStake(slip.getStake());
        slipDto.setTotalOdds(slip.getTotalOdds());
        slipDto.setState(slip.getState());
        slipDto.setBets(new HashSet<>());
        for (Bet bet: slip.getBets()) {
            slipDto.getBets().add(betMapper.mapToBetDto(bet));
        }

        return slipDto;
    }
}
