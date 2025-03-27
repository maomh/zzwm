package site.mhjn.zzwm.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mhjn.zzwm.controller.dto.DemoDTO;
import site.mhjn.zzwm.response.Result;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @PostMapping("/body")
    public Result body(@Valid @RequestBody DemoDTO demoDTO) {
        return Result.success(demoDTO);
    }

}
