package com.github.aleksanderkot00.onlinesportsbetting.facade;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import com.github.aleksanderkot00.onlinesportsbetting.validator.CartSlipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SlipOrderFacade {

    private final CartSlipValidator validator;
    private final UserService userService;

    @Autowired
    public SlipOrderFacade(CartSlipValidator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    public Slip orderSlip(String email) {
        User user = userService.getUser(email);
        validator.validateCartSlip(user);
        userService.orderCartSlip(email);

        return user.getCartSlip();
    }
}
