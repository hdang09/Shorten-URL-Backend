package hdang09.services;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            return Response.notFound("The user is not exist", accounts);
        }

        return Response.ok("Successfully get all user account", accounts);
    }

    public Account createAccount(Account account) {
        return repo.save(account);
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
