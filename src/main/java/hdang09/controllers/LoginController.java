package hdang09.controllers;

import hdang09.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Login")
@RequestMapping("/api/auth")
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService service;

    @Operation(summary = "Login with Google")
    @GetMapping("/google")
    public ResponseEntity<Void> login(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return service.login(oAuth2User);

    }
}
