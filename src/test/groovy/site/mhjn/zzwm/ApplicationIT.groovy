package site.mhjn.zzwm

class ApplicationIT extends SpringIntegrationBootstrap {

    def "test context loads"() {
        expect:
        applicationContext != null
    }
}
