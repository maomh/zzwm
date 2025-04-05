package site.mhjn.zzwm.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("后台管理接口")
                .displayName("后台管理接口")
                .pathsToMatch("/demo/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("公共接口")
                .displayName("公共接口")
                .pathsToMatch("/api/**")
                .build();
    }
}
