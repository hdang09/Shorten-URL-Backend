package hdang09.services;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repo;

    @Autowired
    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Response<List<Account>> getAll() {
        List<Account> accounts = repo.getAll();

        if (accounts.isEmpty()) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "The user is not exist");
        }

        return new Response<>(HttpStatus.OK.value(), "Get all user successfully");
    }

    public Response<Account> createAccount(Account account) {
        try {
            // TODO: Validate email, firstname, lastname

            boolean isAccountExists = repo.getByEmail(account.getEmail()) != null;
            if (isAccountExists) {
                return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email is exist");
            }

            Account newAccount = repo.save(account);
            return new Response<>(HttpStatus.CREATED.value(), "Account created successfully", newAccount);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Failed to create account");
        }
    }

    public Account updateStatus(Status status, int accountId) {
        Account currentAcc = repo.getById(accountId);
        currentAcc.setStatus(status);
        return repo.save(currentAcc);
    }

    public Account updateRole(Role role, int accountId) {
        Account currentAcc = repo.getById(accountId);
        currentAcc.setRole(role);
        return repo.save(currentAcc);
    }

    public Account getUserById(int accountId) {
        return repo.getById(accountId);
    }
}
