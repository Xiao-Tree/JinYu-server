package com.xiaotree.jinyuserver.handler;

import com.xiaotree.jinyuserver.domain.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        logger.error("出现异常1" + ex.getClass().getSimpleName() + "：" + ex.getMessage());
        return super.handleErrorResponseException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        logger.error("出现异常2" + ex.getClass().getSimpleName() + "：" + ex.getMessage());
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error("出现异常3" + ex.getClass().getSimpleName() + "：" + ex.getMessage());
        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    @ExceptionHandler(value = { ServiceException.class })
    public ResponseEntity<Result<Boolean>> handleServerErrorException(ServiceException ex) {
        logger.error("业务异常" + ex.getClass().getSimpleName() + "：" + ex.getMessage());
        return ResponseEntity.badRequest().body(Result.error(ex.getMessage(),false));
    }
}
