package hdang09.services;

import hdang09.constants.Role;
import hdang09.entities.Account;
import hdang09.entities.Report;
import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import hdang09.utils.AuthorizationUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    AuthorizationUtil authorizationUtil;

    @Value("${url.host}")
    String URL_HOST;

    public Response<Report> getByAccountId(int accountId) {
        // Check account
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            // Get report
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

    public Response<Report> getByAccountIdForAdmin(HttpServletRequest request, int accountId) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        return getByAccountId(accountId);
    }

    public Response<Report> getForCurrentAccount(HttpServletRequest request) {
        int accountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        return getByAccountId(accountId);
    }

    public Response<Report> getByAccountIdAndDate(HttpServletRequest request, int accountId, int year, int month) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        // Check account
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        try {
            // Get report
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
