package site.mhjn.zzwm.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


public class AttemptsPasswordEncoder implements PasswordEncoder {
    private final List<PasswordEncoder> passwordEncoders = new ArrayList<>();

    public AttemptsPasswordEncoder() {
        passwordEncoders.add(new BCryptPasswordEncoder());
        passwordEncoders.add(new DirectPasswordEncoder());
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoders.get(0).encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        for (PasswordEncoder passwordEncoder : passwordEncoders) {
            if (passwordEncoder.matches(rawPassword, encodedPassword)) {
                return true;
            }
        }
        return false;
    }
}
