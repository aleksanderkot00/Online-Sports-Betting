package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesDto {

    @JsonProperty("rates")
    private RatesDto rates;

    @JsonProperty("date")
    private LocalDate date;
}
