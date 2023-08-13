package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.JwtPayload;
import hdang09.repositories.AccountRepository;
import hdang09.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AccountRepository repo;

    @Value("${url.client}")
    String URL_CLIENT;

    public ResponseEntity<Void> login() {
        // Generate token
        DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = repo.getByEmail(user.getEmail());

        if (account == null) {
            Account newAccount = new Account(user.getGivenName(), user.getFamilyName(), user.getEmail(), user.getPicture());
            account = repo.save(newAccount);
        }

        JwtPayload jwtPayload = new JwtPayload(account.getId(), account.getEmail(), account.getRole());
        Map<String, Object> payload = jwtPayload.toMap();
        String token = jwtUtil.generateToken(payload);

        // TODO: Check if status of an account is waiting or reject

        // Redirect to URL client
        String url = URL_CLIENT + "/" + "?success=true&token=" + token;
        URI uri = URI.create(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
