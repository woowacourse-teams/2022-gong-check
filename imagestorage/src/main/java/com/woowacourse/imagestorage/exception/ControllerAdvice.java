package com.woowacourse.imagestorage.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(final Exception e) {
        log.error("Stack Trace : {}", extractStackTrace(e));
        return ResponseEntity.internalServerError().build();
    }

    private String extractStackTrace(final Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
