package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.SlipDto;
import org.springframework.stereotype.Component;

@Component
public class SlipMapper {
    public SlipDto mapToSlipDto(Slip slip) {
        SlipDto slipDto = new SlipDto();
        slipDto.setStake(slip.getStake());
        slipDto.setTotalOdds(slip.getTotalOdds());
        slipDto.setState(slip.getState());

        return slipDto;
    }
}
