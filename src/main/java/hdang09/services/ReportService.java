package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.Report;
import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    AccountRepository accountRepository;

    @Value("${url.host}")
    String URL_HOST;

    public Response<Report> getByAccountId(int accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            Report report = urlRepository.getReportByAccountId(accountId);
            List<URL> links = urlRepository.getLinksByAccountId(accountId)
                    .stream()
                    .map(link -> {
                        link.setShortenLink(URL_HOST + "/" + link.getShortenLink());
                        return link;
                    })
                    .collect(Collectors.toList());
            Collections.reverse(links);
            report.setLinks(links);
            return new Response<>(HttpStatus.OK.value(), "Get report successfully", report);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Report does not exist", new Report(accountId));
        }
    }

    public Response<Report> getByAccountIdAndDate(int accountId, int year, int month) {
        Account account = accountRepository.findById(accountId).orElse(null);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            Report report = urlRepository.getReportByAccountIdAndDate(accountId, year, month);
            List<URL> links = urlRepository.getLinksByAccountIdAndDate(accountId, year, month)
                    .stream()
                    .map(link -> {
                        link.setShortenLink(URL_HOST + "/" + link.getShortenLink());
                        return link;
                    })
                    .collect(Collectors.toList());
            Collections.reverse(links);
            report.setLinks(links);
            return new Response<>(HttpStatus.OK.value(), "Get report successfully", report);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Report does not exist", new Report(accountId));
        }
    }
}
