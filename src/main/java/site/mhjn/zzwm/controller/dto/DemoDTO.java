package site.mhjn.zzwm.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DemoDTO {
    @Length(min = 1, max = 8)
    @NotBlank
    private String name;

    @Max(20)
    @Min(1)
    @NotNull
    private Integer age;


}
