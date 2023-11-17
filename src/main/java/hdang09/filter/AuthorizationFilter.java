package hdang09.filter;

import hdang09.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.*;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String PORTFOLIO_PAGE = "/";

    private final List<String> excludedUrls = Arrays.asList("/swagger-ui", "/auth", "/v3/api-docs", "/api/auth/google");

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        if (isUrlExcluded(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            setResponseUnAuthorized(response, "Token empty");
            return;
        }

        if (!authHeader.startsWith("Bearer ") || authHeader.length() > 500) {
            setResponseUnAuthorized(response, "Token invalid");
            return;
        }

        try {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenExpired(token)) {
                setResponseUnAuthorized(response, "Token expired");
                return;
            }

//            Map<String, Object> payloadMap = jwtUtil.extractClaim(token, claims -> claims.get("payload", Map.class));
//            if (jwtUtil.isTokenValid(token, jwtPayloadMapper.mapFromMap(payloadMap))) {
//                setResponseUnAuthorized(response, "Token Invalid");
//                return;
//            }
        } catch (Exception e) {
            setResponseUnAuthorized(response, "Token Invalid");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isUrlExcluded(String url) {
        if (url.equals(PORTFOLIO_PAGE)) return true;

        for (String excludedUrl : excludedUrls) {
            if (url.startsWith(excludedUrl)) {
                return true;
            }
        }
        return false;
    }

    private void setResponseUnAuthorized(HttpServletResponse response, String text) throws java.io.IOException {
        response.setContentType("text/plain");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(text);
    }
}