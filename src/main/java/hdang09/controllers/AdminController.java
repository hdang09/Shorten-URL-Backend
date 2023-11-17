package hdang09.controllers;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.dto.CreateAccountDTO;
import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @Autowired
    AccountService service;

    // TODO: Authorization role with Spring Security and UserDetails
    @Operation(summary = "Get all info users")
    @GetMapping
    public Response<List<Account>> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Create an account")
    @PostMapping("/account")
    public Response<Account> createAccount(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create account, enter role user = \"0\", admin = \"1\"",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateAccountDTO.class)
                    )
            ) CreateAccountDTO account
    ) {
        return service.createAccount(account);
    }

    @Operation(summary = "Update status for user")
    @PutMapping("/status")
    public Response<Account> updateStatus(@RequestParam Status status, @RequestParam int accountId) {
        return service.updateStatus(status, accountId);
    }

    @Operation(summary = "Update role for user")
    @PutMapping("/role")
    public Response<Account> updateRole(@RequestParam Role role, @RequestParam int accountId) {
        return service.updateRole(role, accountId);
    }

}
