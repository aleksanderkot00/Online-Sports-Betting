package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "BET_SLIPS")
public class BetSlip {

    @NotNull
    @Id
    @GeneratedValue
    private long betSlipId;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="BETSLIP_BET", joinColumns = @JoinColumn(name = "BET_SLIP_ID"), inverseJoinColumns = @JoinColumn(name = "BET_ID"))
    private Set<Bet> bets = new HashSet<>();

    @NotNull
    @Column(precision = 9, scale = 2)
    private BigDecimal stake;

    @NotNull
    private BigDecimal totalOdds = BigDecimal.ONE;

    public long getBetSlipId() {
        return betSlipId;
    }

    public void setBetSlipId(long betSlipId) {
        this.betSlipId = betSlipId;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public BigDecimal getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(BigDecimal totalOdds) {
        this.totalOdds = totalOdds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetSlip betSlip = (BetSlip) o;
        return betSlipId == betSlip.betSlipId &&
                Objects.equals(stake, betSlip.stake) &&
                Objects.equals(totalOdds, betSlip.totalOdds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(betSlipId, stake, totalOdds);
    }
}
