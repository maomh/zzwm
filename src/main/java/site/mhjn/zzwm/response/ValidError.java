package site.mhjn.zzwm.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidError {
    private String field;
    private String message;
}
