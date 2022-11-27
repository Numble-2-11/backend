package com.community.numble.exception;

import com.community.numble.common.bean.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResultBean<?>> exception(final Throwable throwable) {
        log.error("오류 발생 : {}", throwable.getMessage());

        final ResultBean<?> resultBean = new ResultBean<>();
        resultBean.setSuccess(false);
        resultBean.setCode(500);
        resultBean.setMessage(throwable.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultBean);
    }
}
