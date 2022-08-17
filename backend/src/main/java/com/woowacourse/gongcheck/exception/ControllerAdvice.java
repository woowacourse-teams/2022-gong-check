package com.woowacourse.gongcheck.exception;

import static com.woowacourse.gongcheck.core.domain.exception.ExceptionMessageGenerator.generate;

import com.woowacourse.gongcheck.core.domain.exception.RequestContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private final RequestContext requestContext;

    public ControllerAdvice(final RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(final CustomException e) {
        log.info(generate(requestContext.getRequest(), e));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final CustomException e) {
        log.warn(generate(requestContext.getRequest(), e));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(final CustomException e) {
        log.info(generate(requestContext.getRequest(), e));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.warn(generate(requestContext.getRequest(), e));
        return ResponseEntity.badRequest().body(ErrorResponse.from(ErrorCode.V001));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(final CustomException e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(final Exception e) {
        log.error("Stack Trace : {}", extractStackTrace(e));
        return ResponseEntity.internalServerError().body(ErrorResponse.from(ErrorCode.E001));
    }

    private String extractStackTrace(final Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
