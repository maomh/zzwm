package site.mhjn.zzwm

import site.mhjn.zzwm.util.JsonUtil
import spock.lang.Specification

class DemoTest extends Specification {

    def "test demo"() {
        when:
        println JsonUtil.toJsonString([name: "zhangsan", age: 18])

        then:
        noExceptionThrown()
    }
}
