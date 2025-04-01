package site.mhjn.zzwm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import site.mhjn.zzwm.exception.BusinessException;

@Component
public class JsonUtil {
    private static ObjectMapper applicationObjectMapper;
    private static final ObjectMapper fixedObjectMapper = new ObjectMapper();
    static {
        fixedObjectMapper.registerModule(new JavaTimeModule());
    }

    private JsonUtil(@Nullable ObjectMapper objectMapper) {
        if (applicationObjectMapper == null) {
            applicationObjectMapper = objectMapper;
        }
    }

    private static ObjectMapper getObjectMapper() {
        if (applicationObjectMapper == null) {
            System.out.println(">>>> fixed object mapper");
            return fixedObjectMapper;
        } else {
            System.out.println(">>>> application object mapper");
            return applicationObjectMapper;
        }
    }

    /**
     * Convert JSON string to object
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new BusinessException("转换对象为JSON字符串失败", e);
        }
    }

    /**
     * Convert JSON string to object
     */
    public static <T> T fromJsonString(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new BusinessException("转换JSON字符串为对象失败", e);
        }
    }
}
