package com.github.aleksanderkot00.onlinesportsbetting.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "BETS")
public class Bet {

    @NotNull
    @Id
    @GeneratedValue
    private long betId;

    @ManyToOne(optional = false)
    @JoinColumn(name="EVENT_ID")
    private Event event;

    @NotNull
    private Results type;

    @NotNull
    private BigDecimal odds;

    @NotNull
    private boolean isActive;

    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Results getType() {
        return type;
    }

    public void setType(Results type) {
        this.type = type;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return betId == bet.betId &&
                isActive == bet.isActive &&
                Objects.equals(event, bet.event) &&
                type == bet.type &&
                Objects.equals(odds, bet.odds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(betId, event, type, odds, isActive);
    }
}
