package site.mhjn.zzwm.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import site.mhjn.zzwm.exception.BusinessException;
import site.mhjn.zzwm.response.Result;
import site.mhjn.zzwm.response.ValidError;
import site.mhjn.zzwm.util.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result> handleValidationError(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<ValidError> validErrors = e.getBindingResult().getFieldErrors().stream()
                .map(f -> new ValidError(f.getField(), f.getDefaultMessage()))
                .toList();

        log.info("Request {} has validation error: {}", request.getRequestURI(), JsonUtil.toJsonString(validErrors));

        return ResponseEntity.badRequest().body(Result.validateError(validErrors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result> handleParameterError(
            ConstraintViolationException e, HttpServletRequest request) {
        log.info("Request URI: {}, Parameter Error: {}", request.getRequestURI(), e.getMessage());
        Result result = Result.parameterError(e.getMessage());
        e.getConstraintViolations().forEach(c -> {
            for (Path.Node node : c.getPropertyPath()) {
                if (node.getKind() == ElementKind.PARAMETER) {
                    result.setMessage("参数 '" + node.getName() + "' " + c.getMessage());
                    break;
                }
            }
        });
        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result> handleBusinessError(BusinessException e, HttpServletRequest request) {
        log.info("Request URI: {}, Business Error: {}", request.getRequestURI(), e.getMessage());
        return ResponseEntity.ok(Result.businessError(e.getMessage()));
    }
}
