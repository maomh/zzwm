package site.mhjn.zzwm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.mhjn.zzwm.Constants;
import site.mhjn.zzwm.config.HasRoleAdmin;
import site.mhjn.zzwm.controller.dto.DemoDTO;
import site.mhjn.zzwm.response.Result;

@RestController
@RequestMapping("/demo")
@Validated
public class DemoController {

    @HasRoleAdmin
    @PostMapping(path = "/body", produces = "application/json", consumes = "application/json")
    @Operation(
            summary = "请求体演示接口",
            description = "请求体演示接口",
            parameters = {
                    @Parameter(
                            name="id-token",
                            description = "JWT认证Token字符串",
                            required = true,
                            in = ParameterIn.HEADER,
                            schema = @Schema(type = "string"),
                            example = Constants.EXAMPLE_OF_ID_TOKEN
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "请求成功",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Result.class, description = "统一的返回结果对象")
                            )
                    )
            }
    )
    public Result body(@Valid @RequestBody DemoDTO demoDTO) {
        return Result.success(demoDTO);
    }

    @Operation(
            summary = "表单提交演示接口",
            description = "表单提交演示接口",
            security = @SecurityRequirement(name = "basicAuth", scopes = {"ROLE_ADMIN"}),
            parameters = {
                    @Parameter(name = "id", required = true, in = ParameterIn.QUERY, example = "1", description = "ID"),
                    @Parameter(name = "type", required = true, in = ParameterIn.QUERY, example = "abc", description = "类型")
            }
    )
    @PostMapping("/form/{id}")
    public Result form(@Valid  DemoDTO demoDTO,
                       @Valid @Min(0) @PathVariable("id") int id,
                       @Valid @Size(min = 1, max = 5) @RequestParam("type") String type) {
        return Result.success(demoDTO);
    }
}
