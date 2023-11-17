package hdang09.controllers;

import hdang09.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login")
@RequestMapping("/api/auth")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class LoginController {

    @Autowired
    LoginService service;

    @Operation(summary = "Login with Google")
    @GetMapping("/google")
    public ResponseEntity<Void> login() {
        return service.login();

    }
}
