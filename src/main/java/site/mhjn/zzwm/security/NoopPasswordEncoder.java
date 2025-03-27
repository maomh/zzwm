package site.mhjn.zzwm.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoopPasswordEncoder implements PasswordEncoder {
    public static final String ID = "noop";

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }
}
