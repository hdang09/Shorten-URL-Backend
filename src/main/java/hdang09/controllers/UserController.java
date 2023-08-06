package hdang09.controllers;

import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User")
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    AccountService service;

    @Operation(summary = "Get an information of a user")
    @GetMapping("/{accountId}")
    public Response<Account> getInfoUser(
            @RequestHeader(value = "token", required = false) String token, @PathVariable("accountId") int accountId) {
        return service.getUserById(accountId);
    }
}
