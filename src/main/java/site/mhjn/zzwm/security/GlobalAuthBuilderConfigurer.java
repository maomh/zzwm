package site.mhjn.zzwm.security;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import site.mhjn.zzwm.util.CollectionUtil;

import java.util.List;

@Slf4j
@Component
public class GlobalAuthBuilderConfigurer extends GlobalAuthenticationConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final List<UserDetailsService> userDetailsServiceList;
    private final List<AuthenticationProvider> authenticationProviderList;

    public GlobalAuthBuilderConfigurer(@NotNull PasswordEncoder passwordEncoder,
                                       @Nullable List<UserDetailsService> userDetailsServiceList,
                                       @Nullable List<AuthenticationProvider> authenticationProviderList) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceList = userDetailsServiceList;
        this.authenticationProviderList = authenticationProviderList;
    }


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        if (CollectionUtil.isNotEmpty(userDetailsServiceList)) {
            for (UserDetailsService userDetailsService : userDetailsServiceList) {
                log.info("Load UserDetailsService: {}", userDetailsService.getClass().getName());
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
            }
        } else {
            log.info("Not found UserDetailsService, use default UserDetailsService");
        }

        if (CollectionUtil.isNotEmpty(authenticationProviderList)) {
            for (AuthenticationProvider authenticationProvider : authenticationProviderList) {
                log.info("Load AuthenticationProvider: {}", authenticationProvider.getClass().getName());
                auth.authenticationProvider(authenticationProvider);
            }
        } else {
            log.info("Not found AuthenticationProvider.");
        }

    }
}
