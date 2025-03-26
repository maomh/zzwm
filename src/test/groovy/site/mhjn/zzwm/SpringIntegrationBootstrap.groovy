package site.mhjn.zzwm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@ActiveProfiles("test")
@Import(TestContainerConfiguration)
@SpringBootTest(classes = Application)
abstract class SpringIntegrationBootstrap extends Specification {

    WebApplicationContext applicationContext

    @Autowired
    def setApplicationContext(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }
}
