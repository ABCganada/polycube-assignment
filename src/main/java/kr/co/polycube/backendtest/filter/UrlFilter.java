package kr.co.polycube.backendtest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class UrlFilter extends OncePerRequestFilter {

    private static final Pattern pattern = Pattern.compile("[^a-zA-Z0-9/?&=:]");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String url = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        //db 접속 위해 통과
        if (url.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (pattern.matcher(url).find()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"reason\": \"유효하지 않은 URL\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
