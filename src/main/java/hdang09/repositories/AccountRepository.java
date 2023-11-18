package hdang09.repositories;

import hdang09.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a")
    List<Account> getAll();

    @Query("SELECT a from Account a WHERE a.email = :email")
    Account getByEmail(@Param("email") String email);
}
