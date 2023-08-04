package hdang09.services;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.entities.Account;
import hdang09.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository repo;

    public List<Account> getAll() {
        return repo.getAll();
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
