package hdang09.controllers;

import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AccountService service;

    @Operation(summary = "Get all info user")
    @GetMapping
    public List<Account> getAll(@RequestHeader(value = "token", required = false) String token) {
        return service.getAll();
    }

    @Operation(summary = "Create account")
    @PostMapping("/account")
    public Account createAccount(
            @RequestHeader(value = "token", required = false) String token,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    description = "Create account, enter role user = 0, admin = 1",
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
    public Account updateStatus(
            @RequestHeader(value = "token", required = false) String token,
            @RequestParam Status status,
            @RequestParam int accountId
    ) {
        return service.updateStatus(status, accountId);
    }

}
