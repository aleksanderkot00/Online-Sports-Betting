package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.RatesDto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesDto {

    @JsonProperty("rates")
    private RatesDto rates;

    @JsonProperty("date")
    private LocalDate date;
}
