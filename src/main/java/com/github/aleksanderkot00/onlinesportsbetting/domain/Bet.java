package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity(name = "BETS")
public class Bet {

    @NotNull
    @Id
    @GeneratedValue
    private long betId;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name="EVENT_ID")
    private Event event;

    @NotNull
    private BetResult type;

    @NotNull
    private BigDecimal odds;

    @NotNull
    private boolean active;
}
