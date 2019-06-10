package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "EXCHANGE_RATES")
public class ExchangeRates {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDate date;

    private BigDecimal euroRate;
    private BigDecimal dollarRate;
    private BigDecimal poundRate;
}
