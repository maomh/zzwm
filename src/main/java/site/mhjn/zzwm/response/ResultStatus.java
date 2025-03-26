package site.mhjn.zzwm.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultStatus {
    SUCCESS(0, "操作成功"),

    VALIDATE_ERROR(1000, "参数校验错误"),
    PARAMETER_ERROR(1001, "参数错误"),

    BUSINESS_ERROR(2000, "业务错误"),

    FAILURE(9999, "操作失败");

    private final int code;
    private final String message;
}
