package hdang09.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtil {

    @Autowired
    private JwtUtil jwtUtil;

    public int getUserIdFromAuthorizationHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return (int) jwtUtil.extractPayload(token).get("id");
    }

    public String getRoleFromAuthorizationHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return (String) jwtUtil.extractPayload(token).get("role");
    }

}