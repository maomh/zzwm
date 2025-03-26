package site.mhjn.zzwm.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import site.mhjn.zzwm.util.CollectionUtil;
import site.mhjn.zzwm.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private int code;
    private String message;
    private Object data;
    private List<ValidError> validErrors;

    public Result(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public void setMessage(String message) {
        if (StringUtil.isNotEmpty(message)) {
            this.message = message;
        }
    }

    /**
     * 返回成功 {@link ResultStatus#SUCCESS}
     * @return Result
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 返回成功 {@link ResultStatus#SUCCESS}
     * @param data 返回的数据
     * @return Result
     */
    public static Result success(Object data) {
        Result result = new Result(ResultStatus.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回操作失败 {@link ResultStatus#FAILURE}
     * @return Result
     */
    public static Result failure() {
        return failure(null);
    }

    /**
     * 返回操作失败 {@link ResultStatus#FAILURE}
     * @param message 失败信息
     * @return Result
     */
    public static Result failure(String message) {
        Result result = new Result(ResultStatus.FAILURE);
        if (StringUtil.isNotEmpty(message)) {
            result.setMessage(message);
        }
        return result;
    }

    /**
     * 返回业务错误 {@link ResultStatus#BUSINESS_ERROR}
     * @param message 错误信息
     * @return Result
     */
    public static Result businessError(String message) {
        Result result = new Result(ResultStatus.BUSINESS_ERROR);
        if (StringUtil.isNotEmpty(message)) {
            result.setMessage(message);
        }
        return result;
    }


    /**
     * 返回 参数校验错误 {@link ResultStatus#VALIDATE_ERROR}
     * @param validErrors 错误信息
     * @return Result
     */
    public static Result validateError(List<ValidError> validErrors) {
        Result result = new Result(ResultStatus.VALIDATE_ERROR);
        if (CollectionUtil.isNotEmpty(validErrors)) {
            result.validErrors = new ArrayList<>(validErrors);
        }
        return result;
    }

    /**
     * 返回 参数错误 {@link ResultStatus#PARAMETER_ERROR}
     * @param message 错误信息
     * @return Result
     */
    public static Result parameterError(String message) {
        Result result = new Result(ResultStatus.PARAMETER_ERROR);
        result.setMessage(message);
        return result;
    }
}
