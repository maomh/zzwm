package site.mhjn.zzwm.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.mhjn.zzwm.controller.dto.DemoDTO;
import site.mhjn.zzwm.response.Result;

@RestController
@RequestMapping("/demo")
@Validated
public class DemoController {

    @PostMapping("/body")
    public Result body(@Valid @RequestBody DemoDTO demoDTO) {
        return Result.success(demoDTO);
    }

    @PostMapping("/form/{id}")
    public Result form(@Valid  DemoDTO demoDTO,
                       @Valid @Min(0) @PathVariable("id") int id,
                       @Valid @Size(min = 1, max = 5) @RequestParam("type") String type) {
        return Result.success(demoDTO);
    }
}
