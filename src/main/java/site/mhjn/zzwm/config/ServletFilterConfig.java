package site.mhjn.zzwm.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.mhjn.zzwm.filter.RequestInfoFilter;

@Configuration
public class ServletFilterConfig {

    @Bean
    public FilterRegistrationBean<RequestInfoFilter> requestInfoFilter() {
        FilterRegistrationBean<RequestInfoFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RequestInfoFilter());
        filterRegistrationBean.addUrlPatterns("/demo/*", "/login");
        filterRegistrationBean.setOrder(-1);
        return filterRegistrationBean;
    }
}
