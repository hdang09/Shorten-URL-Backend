package hdang09.services;

import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.entities.JwtPayload;
import hdang09.repositories.AccountRepository;
import hdang09.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    AccountRepository repo;
    @Value("${url.client}")
    String URL_CLIENT;
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<Void> login() {
        // Generate token
        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = repo.getByEmail(user.getEmail());

        // Create new account if not exist
        if (account == null) {
            Account newAccount = new Account(user.getGivenName(), user.getFamilyName(), user.getEmail(), user.getPicture());
            account = repo.save(newAccount);
        }

        // Check if status of an account is waiting or reject
        if (!account.getStatus().equals(Status.ACCEPT)) {
            String url = URL_CLIENT + "/" + "?success=false&status=" + account.getStatus();
            URI uri = URI.create(url);
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }

        // Generate token
        JwtPayload jwtPayload = new JwtPayload(account.getId(), account.getEmail(), account.getRole());
        Map<String, Object> payload = jwtPayload.toMap();
        String token = jwtUtil.generateToken(payload);

        // Redirect to URL client
        String url = URL_CLIENT + "/" + "?success=true&status=" + account.getStatus() + "&token=" + token;
        URI uri = URI.create(url);

        // Remove JSESSIONID
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        assert response != null;
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
