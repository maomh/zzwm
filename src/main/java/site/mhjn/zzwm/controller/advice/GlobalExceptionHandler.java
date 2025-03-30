package site.mhjn.zzwm.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import site.mhjn.zzwm.exception.BusinessException;
import site.mhjn.zzwm.response.Result;
import site.mhjn.zzwm.response.ValidError;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.info("Request URI: {}, Method Argument Not Valid Exception: {}", request.getRequestURI(), e.getBindingResult());
        List<ValidError> validErrors = e.getBindingResult().getFieldErrors().stream()
                .map(f -> new ValidError(f.getField(), f.getDefaultMessage()))
                .toList();
        return ResponseEntity.ok(Result.validateError(validErrors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.info("Request URI: {}, Business Exception: {}", request.getRequestURI(), e.getMessage());
        return ResponseEntity.ok(Result.businessError(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handleUnknownException(Exception e, HttpServletRequest request) throws Exception {
        // 如果是Spring MVC的常规异常，则直接抛出，交给Spring MVC处理
        if (e instanceof ErrorResponse error) {
            log.info("Request URI: {}, error status code: {}, error message: {}", request.getRequestURI(), error.getStatusCode(), e.getMessage());
            throw e;
        }
        log.error("Request URI: {}, Unknown Exception: {}", request.getRequestURI(), e.getMessage(), e);
        return ResponseEntity.ok(Result.failure());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result> handleConstraintViolationException(
            ConstraintViolationException e, HttpServletRequest request) {

        log.info("Request URI: {}, Constrain Violation Exception: {}", request.getRequestURI(), e.getMessage());
        Result result = Result.parameterError(e.getMessage());

        e.getConstraintViolations().forEach(c -> {
            for (Path.Node node : c.getPropertyPath()) {
                if (node.getKind() == ElementKind.PARAMETER) {
                    result.setMessage("参数 '" + node.getName() + "' " + c.getMessage());
                    break;
                }
            }
        });

        return ResponseEntity.ok(result);
    }
}
