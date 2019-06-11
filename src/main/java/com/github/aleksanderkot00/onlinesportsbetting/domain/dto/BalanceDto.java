package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BalanceDto {
    private LocalDate rateDate;
    private BigDecimal plnBalance;
    private BigDecimal eurBalance;
    private BigDecimal usdBalance;
    private BigDecimal gbpBalance;
}
