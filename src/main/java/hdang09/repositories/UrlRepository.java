package hdang09.repositories;

import hdang09.entities.URL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UrlRepository extends CrudRepository<URL, Integer> {

    @Query("SELECT u.originLink FROM URL u WHERE u.shortenLink = :shortenLink")
    String getOriginLinkByLinkcode(String shortenLink);
}
