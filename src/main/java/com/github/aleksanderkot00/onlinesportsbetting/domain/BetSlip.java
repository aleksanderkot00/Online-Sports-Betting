package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity(name = "BET_SLIPS")
public class BetSlip {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BET_ID")
    private Set<Bet> bets;

    @NotNull
    private BigDecimal stake;

    private BigDecimal totalOdds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public void setBets(Set<Bet> bets) {
        this.bets = bets;
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
        return id == betSlip.id &&
                Objects.equals(bets, betSlip.bets) &&
                Objects.equals(stake, betSlip.stake) &&
                Objects.equals(totalOdds, betSlip.totalOdds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bets, stake, totalOdds);
    }
}
