package site.mhjn.zzwm.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

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
}
