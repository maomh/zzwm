package site.mhjn.zzwm.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import site.mhjn.zzwm.util.RequestUtil;

import java.io.IOException;

@Slf4j
public class RequestInfoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("Request Info: {}{}", System.lineSeparator(), RequestUtil.getRequestInfo(request));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
