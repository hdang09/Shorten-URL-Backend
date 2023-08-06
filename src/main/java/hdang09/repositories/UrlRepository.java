package hdang09.repositories;

import hdang09.entities.Report;
import hdang09.entities.URL;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlRepository extends CrudRepository<URL, Integer> {

    @Query("SELECT u FROM URL u WHERE u.shortenLink = :shortenLink")
    URL findByShortenLink(@Param("shortenLink") String shortenLink);

    @Query("SELECT u FROM URL u WHERE u.originLink = :originLink AND u.accountId = :accountId")
    URL checkDuplicate(@Param("originLink") String originLink, @Param("accountId") int accountId);

    @Query("SELECT NEW hdang09.entities.Report(u.accountId, SUM(u.clicks), COUNT(u.shortenLink)) "
            + "FROM URL u WHERE u.accountId = :accountId")
    Report getReportByAccountId(@Param("accountId") int accountId);

    @Query("SELECT u FROM URL u WHERE u.accountId = :accountId")
    List<URL> getLinksByAccountId(@Param("accountId") int accountId);

    @Query("SELECT NEW hdang09.entities.Report(u.accountId, SUM(u.clicks), COUNT(u.shortenLink)) " +
            "FROM URL u WHERE u.accountId = :accountId  AND MONTH(u.createdAt) = :month AND YEAR(u.createdAt) = :year")
    Report getReportByAccountIdAndDate(
            @Param("accountId") int accountId, @Param("year") int year, @Param("month") int month
    );

    @Query("SELECT u FROM URL u " +
            "WHERE u.accountId = :accountId AND MONTH(u.createdAt) = :month AND YEAR(u.createdAt) = :year")
    List<URL> getLinksByAccountIdAndDate(
            @Param("accountId") int accountId, @Param("year") int year, @Param("month") int month
    );


}
