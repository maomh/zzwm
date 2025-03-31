package site.mhjn.zzwm.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DemoDTO {
    @Length(min = 1, max = 8)
    @NotBlank
    @Schema(description = "姓名", example = "张三")
    private String name;

    @Max(20)
    @Min(1)
    @NotNull
    @Schema(description = "年龄", example = "18")
    private Integer age;


}
