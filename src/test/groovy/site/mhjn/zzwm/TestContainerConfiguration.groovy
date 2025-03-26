package site.mhjn.zzwm

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.GenericContainer

@TestConfiguration(proxyBeanMethods = false)
class TestContainerConfiguration {

    @Bean
    @ServiceConnection("redis")
    def redisContainer() {
        return new GenericContainer("redis:latest").withExposedPorts(6379)
    }

}
