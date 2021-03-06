package com.github.aleksanderkot00.onlinesportsbetting.validator;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.exception.NotValidCartSlipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CartSlipValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartSlipValidator.class);

    public void validateCartSlip(User user) {
        Set<Bet> bets = user.getCartSlip().getBets();
        boolean correct =
                validateUserBalance(user) &&
                validateBetsActivity(bets) &&
                validateUniqueOfEvents(bets) &&
                isNotEmpty(bets);
        if (!correct) {
            throw new NotValidCartSlipException();
        }
    }

    private boolean validateBetsActivity(Set<Bet> bets) {
        long activeBetsNumber = bets.stream()
                .filter(Bet::isActive)
                .count();

        boolean hasActiveBet = activeBetsNumber == bets.size() ;

        if (hasActiveBet) {
            LOGGER.info("Cart slip has only active events.");
        } else {
            LOGGER.warn("Cart slip has inactive events!");
        }
        return hasActiveBet;
    }

    private boolean validateUniqueOfEvents(Set<Bet> bets) {
        long betsIdSet = bets.stream()
                .map(bet -> bet.getEvent())
                .distinct()
                .count();

        boolean hasUniqueEvents = betsIdSet == bets.size();

        if (hasUniqueEvents) {
            LOGGER.info("Cart slip has bets from different events.");
        } else {
            LOGGER.warn("Cart slip has bets from the same events!");
        }
        return hasUniqueEvents;
    }

    private boolean isNotEmpty(Set<Bet> bets) {
        boolean isNotEmpty = bets.size() > 0;

        if (isNotEmpty) {
            LOGGER.info("Cart slip is not empty.");
        } else {
            LOGGER.warn("Cart slip is empty!");
        }
        return isNotEmpty;
    }

    private boolean validateUserBalance(User user) {
        boolean hasEnoughFunds = user.getBalance().compareTo(user.getCartSlip().getStake()) >= 0;

        if (hasEnoughFunds) {
            LOGGER.info("User " + user.getEmail() + " has enough funds on the account.");
        } else {
            LOGGER.warn("User " + user.getEmail() + " has not enough funds on the account!");
        }
        return hasEnoughFunds;
    }
}
