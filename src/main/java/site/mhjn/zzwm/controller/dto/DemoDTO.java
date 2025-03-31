package site.mhjn.zzwm.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "演示DTO")
public class DemoDTO {
    @Size(max = 8, min = 1)
    @NotBlank
    @Schema(description = "姓名", example = "张三")
    private String name;

    @NotNull
    @Min(0)
    @Max(20)
    @Schema(description = "年龄", example = "18")
    private Integer age;
}
