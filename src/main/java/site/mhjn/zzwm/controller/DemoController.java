package site.mhjn.zzwm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.mhjn.zzwm.Constants;
import site.mhjn.zzwm.config.HasRoleAdmin;
import site.mhjn.zzwm.controller.dto.DemoDTO;
import site.mhjn.zzwm.response.Result;

import java.util.Map;

@RestController
@RequestMapping("/demo")
@Validated
@Tag(name = "DemoController", description = "演示接口")
public class DemoController {

    @HasRoleAdmin
    @PostMapping(path = "/body", produces = "application/json", consumes = "application/json")
    @Operation(summary = "请求体提交")
    public Result body(@Valid @RequestBody DemoDTO demoDTO) {
        return Result.success(demoDTO);
    }

    @Operation(summary = "普通表单提交")
    @PostMapping(path = "/form/{id}", produces = "application/json", consumes = "multipart/form-data")
    public Result form(@Valid DemoDTO demoDTO,
                       @Nullable MultipartFile file,
                       @Valid @Min(0) @PathVariable("id") int id,
                       @Valid @Size(min = 1, max = 5) @RequestParam("type") String type) {
        if (file != null) {
            System.out.println("File name: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());
        }
        if (demoDTO != null) {
            System.out.println("Name: " + demoDTO.getName());
            System.out.println("Age: " + demoDTO.getAge());
        }
        System.out.println("ID: " + id);
        System.out.println("Type: " + type);
        return Result.success(Map.of(
                "id", id,
                "type", type,
                "name", demoDTO != null ? demoDTO.getName() : "",
                "age", demoDTO != null ? demoDTO.getAge() : "",
                "fileName", file != null ? file.getOriginalFilename() : ""
        ));
    }
}
