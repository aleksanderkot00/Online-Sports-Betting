package com.github.aleksanderkot00.onlinesportsbetting.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

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
    private BetType type;

    @NotNull
    private BigDecimal odds;

    @NotNull
    private boolean active = true;

    @NotNull
    private BetResult result = BetResult.NOT_FINISHED;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return betId == bet.betId &&
                active == bet.active &&
                type == bet.type &&
               odds.compareTo(bet.odds) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(betId, type, odds, active);
    }

    public void settle() {
        if (event.isFinished()) {
            active = false;
            switch (type) {
                case ONE:
                    result = (event.getTeamOneScore().compareTo(event.getTeamTwoScore()) > 0) ? BetResult.WINNING : BetResult.LOST;
                    break;
                case TWO:
                    result = (event.getTeamOneScore().compareTo(event.getTeamTwoScore()) < 0) ? BetResult.WINNING : BetResult.LOST;
                    break;
                case ZERO:
                    result = (event.getTeamOneScore().compareTo(event.getTeamTwoScore()) == 0) ? BetResult.WINNING : BetResult.LOST;
                    break;
                case ZERO_ONE:
                    result = (event.getTeamOneScore().compareTo(event.getTeamTwoScore()) >= 0) ? BetResult.WINNING : BetResult.LOST;
                    break;
                case ZERO_TWO:
                    result = (event.getTeamOneScore().compareTo(event.getTeamTwoScore()) <= 0) ? BetResult.WINNING : BetResult.LOST;
                    break;
            }
        }
    }
}
