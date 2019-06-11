package com.github.aleksanderkot00.onlinesportsbetting.api.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesDto {

    @JsonProperty("EUR")
    private BigDecimal euroRate;

    @JsonProperty("USD")
    private BigDecimal dollarRate;

    @JsonProperty("GBP")
    private BigDecimal poundRate;
}