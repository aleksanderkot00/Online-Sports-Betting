package com.github.aleksanderkot00.onlinesportsbetting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class NotValidCartSlipException extends RuntimeException {
}
