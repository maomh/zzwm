package site.mhjn.zzwm.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Validation 校验错误信息")
public class ValidError {
    @Schema(description = "校验的字段名", example = "name")
    private String field;
    @Schema(description = "校验的错误信息", example = "姓名不能为空")
    private String message;
}
