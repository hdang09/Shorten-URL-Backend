package hdang09.services;

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
}
