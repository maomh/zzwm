package site.mhjn.zzwm.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

@UtilityClass
public class RequestUtil {

    public static String getRequestInfo(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("         Method: ").append(request.getMethod()).append(System.lineSeparator());
        sb.append("            URI: ").append(request.getRequestURI()).append(System.lineSeparator());
        sb.append("            URL: ").append(request.getRequestURL()).append(System.lineSeparator());
        sb.append("    QueryString: ").append(request.getQueryString()).append(System.lineSeparator());
        sb.append("        Headers: ").append(System.lineSeparator());
        request.getHeaderNames().asIterator().forEachRemaining(name ->
                sb.append("          ").append(name).append("=").append(request.getHeader(name)).append(System.lineSeparator()));

        return sb.toString();
    }

    public static RequestMatcher antMatches(String ... patterns) {
        RequestMatcher[] matchers = new RequestMatcher[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            matchers[i] = AntPathRequestMatcher.antMatcher(patterns[i]);
        }
        return RequestMatchers.anyOf(matchers);
    }

    public static RequestMatcher mediaTypes(MediaType... mediaTypes) {
        RequestMatcher[] matchers = new RequestMatcher[mediaTypes.length];
        for (int i = 0; i < mediaTypes.length; i++) {
            matchers[i] = new MediaTypeRequestMatcher(mediaTypes[i]);
        }
        return RequestMatchers.anyOf(matchers);
    }
}
