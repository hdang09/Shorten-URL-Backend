package hdang09.repositories;

import hdang09.entities.URL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UrlRepository extends CrudRepository<URL, Integer> {

    @Query("SELECT u FROM URL u WHERE u.shortenLink = :shortenLink")
    URL findByShortenLink(@Param("shortenLink") String shortenLink);

    @Query("SELECT u FROM URL u WHERE u.originLink = :originLink AND u.accountId = :accountId")
    URL checkDuplicate(@Param("originLink") String originLink, @Param("accountId") int accountId);
}
