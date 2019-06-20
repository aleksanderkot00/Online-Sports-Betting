package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@NamedNativeQuery(
        name = "ExchangeRates.getLastRates",
        query = "SELECT * FROM exchange_rates ORDER BY date DESC LIMIT 1",
        resultClass = ExchangeRates.class
)
@Data
@Entity(name = "EXCHANGE_RATES")
public class ExchangeRates {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDate date;

    @Column(precision = 5, scale = 3)
    @Min(value = 0)
    private BigDecimal euroRate;

    @Column(precision = 5, scale = 3)
    @Min(value = 0)
    private BigDecimal dollarRate;

    @Column(precision = 5, scale = 3)
    @Min(value = 0)
    private BigDecimal poundRate;
}
