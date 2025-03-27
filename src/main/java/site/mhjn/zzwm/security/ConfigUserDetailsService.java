package site.mhjn.zzwm.security;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import site.mhjn.zzwm.util.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ConfigUserDetailsService implements UserDetailsService {

    @Resource
    private ConfigUserProperties configUserProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (CollectionUtil.isNotEmpty(configUserProperties.users)) {
            for (ConfigUser user : configUserProperties.users) {
                if (user.username.equals(username)) {
                    return User.withUsername(user.username)
                            .password(user.password)
                            .roles(user.roles)
                            .build();
                }
            }
        }
        return null;
    }

    @Setter
    @Slf4j
    @Component
    @ConfigurationProperties(prefix = "custom.security")
    public static class ConfigUserProperties {
        private List<ConfigUser> users;
        private boolean showLogging = true;

        @PostConstruct
        void init() {
            // Check duplicate username
            if (CollectionUtil.isNotEmpty(users)) {
                Set<String> usernames = new HashSet<>();
                for (ConfigUser user : users) {
                    if (!usernames.add(user.username)) {
                        throw new IllegalArgumentException("Duplicate username: " + user.username);
                    }
                }
            }

            // Logging
            if (showLogging) {
                StringBuilder sb = new StringBuilder();
                if (CollectionUtil.isEmpty(users)) {
                    sb.append(System.lineSeparator()).append("    <empty>");
                } else {
                    for (ConfigUser user : users) {
                        sb.append(System.lineSeparator()).append("    ").append(user);
                    }
                }
                log.info("Load users from configuration: {}", sb);
            }
        }
    }

    @Setter
    public static class ConfigUser {
        private String username;
        private String password;
        private String[] roles = {"USER"};

        @Override
        public String toString() {
            return username + ":" + password + ":" + String.join(",", roles);
        }
    }
}
