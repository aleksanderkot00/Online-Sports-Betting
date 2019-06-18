package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesDto {

    @JsonProperty("EUR")
    private BigDecimal euroRate;

    @JsonProperty("USD")
    private BigDecimal dollarRate;

    @JsonProperty("GBP")
    private BigDecimal poundRate;
}
