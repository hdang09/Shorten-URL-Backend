package hdang09.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class LoginService {

    final String URL_CLIENT = "http://localhost:3000";

    public ResponseEntity<Void> login(OAuth2User oAuth2User) {
        String token = "ey123456789";
        String url = URL_CLIENT + "/" + "?success=true&token=" + token;
        URI uri = URI.create(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
