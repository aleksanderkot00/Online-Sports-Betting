package com.github.aleksanderkot00.onlinesportsbetting.domain.details;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Entity
public class LoginTryDateTime {

    @NotNull
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDateTime dateTime;

    public LoginTryDateTime() {
        dateTime = LocalDateTime.now();
    }
}
