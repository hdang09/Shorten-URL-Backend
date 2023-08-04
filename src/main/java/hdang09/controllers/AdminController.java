package hdang09.controllers;

import hdang09.entities.Account;
import hdang09.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AccountService service;

    @GetMapping
    public List<Account> getAll(@RequestHeader(value = "token") String token) {
        return service.getAll();
    }

    @PostMapping("/account")
    public Account createAccount(@RequestHeader(value = "token") String token, @RequestBody Account account) {
        return service.createAccount(account);
    }

}
