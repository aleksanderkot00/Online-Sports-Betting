package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.validator.CartSlipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlipOrderFacade {

    private final CartSlipValidator validator;
    private final UserRepository userRepository;

    @Autowired
    public SlipOrderFacade(CartSlipValidator validator, UserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    public Slip orderSlip(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        validator.validateCartSlip(user);
        Slip cartSlip = user.getCartSlip();
        user.setBalance(user.getBalance().subtract(cartSlip.getStake()));
        cartSlip.setState(SlipState.ORDERED);
        user.getSlips().add(cartSlip);
        user.setCartSlip(new Slip());
        userRepository.save(user);

        return cartSlip;
    }
}
