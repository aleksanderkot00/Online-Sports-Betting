package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

@Data
@Entity(name = "BET_SLIPS")
public class BetSlip {

    @NotNull
    @Id
    @GeneratedValue
    private long betSlipId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "BETSLIP_BET", joinColumns = @JoinColumn(name = "BET_SLIP_ID"), inverseJoinColumns = @JoinColumn(name = "BET_ID"))
    private List<Bet> bets = new ArrayList<>();

    @NotNull
    @Column(precision = 9, scale = 2)
    private BigDecimal stake;

    @NotNull
    private BigDecimal totalOdds = BigDecimal.ONE;
}
