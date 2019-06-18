package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.validator.CartSlipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class OrderSlipFacade {

    private final CartSlipValidator validator;
    private final UserRepository userRepository;

    @Autowired
    public OrderSlipFacade(CartSlipValidator validator, UserRepository userRepository) {

        this.validator = validator;
        this.userRepository = userRepository;
    }

    public Slip orderSlip(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validator.validateCartSlip(user);
        Slip cartSlip = user.getCartSlip();
        user.addToBalance(cartSlip.getStake().negate());
        cartSlip.setState(SlipState.ORDERED);
        user.getSlips().add(cartSlip);
        user.setCartSlip(new Slip());
        user.getCartSlip().setUser(user);
        userRepository.save(user);

        return cartSlip;
    }
}
