package hdang09.services;

import hdang09.constants.Role;
import hdang09.entities.JwtPayload;
import hdang09.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private JwtUtil jwtUtil;

    final String URL_CLIENT = "http://localhost:3000";

    public ResponseEntity<Void> login() {
        // Generate token
        // TODO: Implement login with Google
        JwtPayload jwtPayload = new JwtPayload(1, "test@gmail.com", Role.ADMIN);
        Map<String, Object> payload = jwtPayload.toMap();
        String token = jwtUtil.generateToken(payload);

        // TODO: Check if status of an account is waiting or reject

        // Redirect to URL client
        String url = URL_CLIENT + "/" + "?success=true&token=" + token;
        URI uri = URI.create(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
