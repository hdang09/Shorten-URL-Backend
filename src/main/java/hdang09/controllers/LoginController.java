package hdang09.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Login")
@RequestMapping("/api/auth")
public class LoginController {
    @Operation(summary = "Login with Google")
    @GetMapping("/google")
    public void loginGoogle() {
        // TODO: Add code login with Google
    }
}
