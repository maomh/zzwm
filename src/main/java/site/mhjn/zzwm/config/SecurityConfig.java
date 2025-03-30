package site.mhjn.zzwm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import site.mhjn.zzwm.security.NoopPasswordEncoder;

import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/acutator/**").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs").permitAll()
                                .requestMatchers("/demo/**").hasRole("DEBUG")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .requestCache(RequestCacheConfigurer::disable)
                .servletApi(withDefaults());

        return http.build();
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
                """);
    }
}
