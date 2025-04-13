package site.mhjn.zzwm.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import site.mhjn.zzwm.security.NoopPasswordEncoder;

import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    public static final String[] IGNORE_URLS = {
            "/actuator/**",
            "/error",
            "/favicon.ico",
            "/webjars/**"
    };
    public static final String[] SWAGGER_URLS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    };
    private static final String[] ADMIN_URLS = {
            "/admin/**",
            "/demo/**",
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        String bcrypt = "bcrypt";
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String noop = "noop";
        NoopPasswordEncoder noopPasswordEncoder = new NoopPasswordEncoder();
        Map<String, PasswordEncoder> encoders = Map.of(
                bcrypt, bcryptPasswordEncoder,
                noop, noopPasswordEncoder
        );
        // 默认使用 bcrypt 加密 - 加密结果是 "{bcrypt}$2a$10$..."
        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(bcrypt, encoders);
        // 如果密码没有"{xxx}"前缀，则使用 noop 进行匹配
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(noopPasswordEncoder);
        return delegatingPasswordEncoder;
    }

    @Bean
    @Order(-1)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(a -> a
                        .requestMatchers(IGNORE_URLS).permitAll()
                        .requestMatchers(SWAGGER_URLS).hasRole("SWAGGER")
                        .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .servletApi(withDefaults())
                .build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("""
                ROLE_DEBUG > ROLE_USER
                ROLE_DEBUG > ROLE_ADMIN
                ROLE_DEBUG > ROLE_SWAGGER
                """);
    }

    public static class LoginSuccessRouter implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) {
            req.isUserInRole("ADMIN");
        }
    }
}
