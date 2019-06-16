package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.details.SlipOrderDetails;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.SlipOrderDetailsRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.validator.CartSlipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@EnableAspectJAutoProxy
@Service
public class OrderSlipFacade {

    private final CartSlipValidator validator;
    private final UserRepository userRepository;
    private final SlipOrderDetailsRepository slipOrderDetailsRepository;

    @Autowired
    public OrderSlipFacade(CartSlipValidator validator, UserRepository userRepository, SlipOrderDetailsRepository slipOrderDetailsRepository) {

        this.validator = validator;
        this.userRepository = userRepository;
        this.slipOrderDetailsRepository = slipOrderDetailsRepository;
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

        slipOrderDetailsRepository.save(
                SlipOrderDetails.builder()
                .odds(cartSlip.getTotalOdds())
                .orderDateTime(LocalDateTime.now())
                .slip(cartSlip)
                .stoke(cartSlip.getStake())
                .build()
        );

        return cartSlip;
    }
}
