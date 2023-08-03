package hdang09.repositories;

import hdang09.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a")
    List<Account> getAll();
}
