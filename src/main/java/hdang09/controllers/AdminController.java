package hdang09.controllers;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin")
@CrossOrigin
public class AdminController {

    @Autowired
    AccountService service;

    @Operation(summary = "Get all info user")
    @GetMapping
    public Response<List<Account>> getAll(@RequestHeader(value = "token", required = false) String token) {
        return service.getAll();
    }

    @Operation(summary = "Create an account")
    @PostMapping("/account")
    public Response<Account> createAccount(
            @RequestHeader(value = "token", required = false) String token,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Create account, enter role user = \"0\", admin = \"1\"",
//                    required = true,
//                    content = @Content(
//                            schema = @Schema(implementation = Account.class)
//                    )
//            ) Account account
            @RequestBody Account account
    ) {
        return service.createAccount(account);
    }

    @Operation(summary = "Update status for user")
    @PutMapping("/status")
    public Response<Account> updateStatus(
            @RequestHeader(value = "token", required = false) String token,
            @RequestParam Status status,
            @RequestParam int accountId
    ) {
        return service.updateStatus(status, accountId);
    }

    @Operation(summary = "Update role for user")
    @PutMapping("/role")
    public Response<Account> updateRole(
            @RequestHeader(value = "token", required = false) String token,
            @RequestParam Role role,
            @RequestParam int accountId
    ) {
        return service.updateRole(role, accountId);
    }

}
