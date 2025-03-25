package site.mhjn.zzwm;

import org.springframework.boot.SpringApplication;

public class TestZzwmApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
