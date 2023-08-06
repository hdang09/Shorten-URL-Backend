package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.Report;
import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    AccountRepository accountRepository;


    public Response<Report> getByAccountId(int accountId) {
        Account account = accountRepository.getById(accountId);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            Report report = urlRepository.getReportByAccountId(accountId);
            List<URL> links = urlRepository.getLinksByAccountId(accountId);
            report.setLinks(links);
            return new Response<>(HttpStatus.OK.value(), "Get report successfully", report);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Report does not exist", new Report(accountId));
        }
    }

    public Response<Report> getByAccountIdAndDate(int accountId, int year, int month) {
        Account account = accountRepository.getById(accountId);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            Report report = urlRepository.getReportByAccountIdAndDate(accountId, year, month);
            List<URL> links = urlRepository.getLinksByAccountIdAndDate(accountId, year, month);
            report.setLinks(links);
            return new Response<>(HttpStatus.OK.value(), "Get report successfully", report);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Report does not exist", new Report(accountId));
        }
    }
}
