package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.details.LoginTryDateTime;
import org.springframework.data.repository.CrudRepository;

public interface LoginTryDateTimeRepository extends CrudRepository<LoginTryDateTime, Long> {
    @Override
    LoginTryDateTime save(LoginTryDateTime loginTryDateTime);
}
