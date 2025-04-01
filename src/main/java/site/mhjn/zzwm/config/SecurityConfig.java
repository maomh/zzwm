package site.mhjn.zzwm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.cors.CorsConfiguration;
import site.mhjn.zzwm.security.NoopPasswordEncoder;
import site.mhjn.zzwm.util.RequestUtil;

import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity(debug = true)
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
    @Order(-1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/demo/**").hasRole("DEBUG")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .requestCache(RequestCacheConfigurer::disable)
                .servletApi(withDefaults())
                .build();
    }

    @Bean
    @Order(-2)
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(
                        "/swagger-ui.html", "/swagger-ui/**",
                        "/v3/api-docs", "/v3/api-docs/**",
                        "/default-ui.css", "/login", "/logout"
                )
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/default-ui.css").permitAll()
                                .anyRequest().hasRole("SWAGGER")
                )
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer.successHandler((req, resp, auth) ->
                                resp.sendRedirect("/swagger-ui.html"))
                )
                .logout(withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .requestCache(withDefaults())
                .servletApi(withDefaults())
                .build();
    }

    @Bean
    public WebSecurityCustomizer ignoringResourcesCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers(
                        "/actuator/**",
                        "/error",
                        "/favicon.ico",
                        "/webjars/**"
                );
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
}
