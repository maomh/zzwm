package site.mhjn.zzwm.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class JwtAuthenticateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 在这里可以添加 JWT 认证的逻辑
        // 例如，检查请求头中的 JWT 是否有效


        // 如果 JWT 有效，则继续执行下一个过滤器或请求处理器
        filterChain.doFilter(servletRequest, servletResponse);

        // 如果 JWT 无效，则可以返回错误响应
        throw new AccessDeniedException("Access denied");
    }
}
