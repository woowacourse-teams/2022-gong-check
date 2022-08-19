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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(final BusinessException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(FileIOException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(final FileIOException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(FileIONotFoundException.class)
    public ResponseEntity<Void> handleFileNotFoundException(final FileIONotFoundException e) {
        log.warn(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FileConvertException.class)
    public ResponseEntity<ErrorResponse> handleFileConvertException(final FileConvertException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(FileResizeException.class)
    public ResponseEntity<ErrorResponse> handleFileResizeException(final FileResizeException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().build();
    }

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
