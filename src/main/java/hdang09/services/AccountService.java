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
            return new Response<>(HttpStatus.NOT_FOUND.value(), "The account list is empty");
        }

        return new Response<>(HttpStatus.OK.value(), "Get all user successfully", accounts);
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

    public Response<Account> updateStatus(Status status, int accountId) {
        Account account = repo.getById(accountId);

        if (account == null) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "The user does not exist");
        }

        account.setStatus(status);
        return new Response<>(HttpStatus.OK.value(), "Update status successfully", repo.save(account));
    }

    public Response<Account> updateRole(Role role, int accountId) {
        Account account = repo.getById(accountId);

        if (account == null) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "The user does not exist");
        }

        if (account.getStatus() != Status.ACCEPT) {
            return new Response<>(
                    HttpStatus.UNAUTHORIZED.value(), "The user can not update role (status must be accept)"
            );
        }

        account.setRole(role);
        return new Response<>(HttpStatus.OK.value(), "Update status successfully", repo.save(account));
    }

    public Response<Account> getUserById(int accountId) {
        Account account = repo.getById(accountId);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        return new Response<>(HttpStatus.OK.value(), "Get user info successfully", account);
    }
}
