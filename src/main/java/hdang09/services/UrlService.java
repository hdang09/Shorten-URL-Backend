package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import hdang09.utils.AuthorizationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UrlService {

    private static final String PORTFOLIO_PAGE = "https://portfolio.hdang09.tech";
    private final UrlRepository urlRepository;
    private final AccountRepository accountRepository;
    private final AuthorizationUtil authorizationUtil;


    @Value("${url.client}")
    String URL_CLIENT;

    @Autowired
    public UrlService(UrlRepository urlRepository, AccountRepository accountRepository, AuthorizationUtil authorizationUtil) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
        this.authorizationUtil = authorizationUtil;
    }

    public ResponseEntity<Void> home() {
        URI uri = URI.create(PORTFOLIO_PAGE);
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    public ResponseEntity<Void> redirect(String linkcode) {
        // Find url by linkcode
        URL url = urlRepository.findByShortenLink(linkcode.trim());

        // Check whether url exists or not
        if (url == null) {
            final String NOT_FOUND_PAGE = URL_CLIENT + "/404";
            URI uri = URI.create(NOT_FOUND_PAGE);
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }

        // Check expired url
        if (url.getExpiredAt() != null && url.getExpiredAt().isBefore(LocalDateTime.now())) {
            final String NOT_FOUND_PAGE = URL_CLIENT + "/404";
            URI uri = URI.create(NOT_FOUND_PAGE);
            urlRepository.delete(url);
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
        }

        // Increase clicks
        int clicks = url.getClicks() + 1;
        url.setClicks(clicks);
        urlRepository.save(url);

        // Redirect
        URI uri = URI.create(url.getOriginLink());
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    public Response<URL> shortenLink(HttpServletRequest request, String originLink, String linkcode) {
        // TODO: Validate originLink, linkcode (.trim())
        int accountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);

        // Check whether account exists or not
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account not found");
        }

        return shorten(originLink, linkcode, account);
    }

    private Response<URL> shorten(String originLink, String linkcode, Account account) {
        int NO_ACCOUNT_ID = -1;

        // Check duplicate origin link in the account
        URL duplicateUrl = urlRepository.checkDuplicate(originLink.trim(), account == null ? NO_ACCOUNT_ID : account.getId());
        if (duplicateUrl != null) {
            return new Response<>(HttpStatus.OK.value(), "The link created before", duplicateUrl);
        }

        // Check if shorten link exist
        boolean isLinkcodeExist = urlRepository.findByShortenLink(linkcode.trim()) != null;
        if (isLinkcodeExist) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
        }

        // Get title from document object.
        String title;
        try {
            title = Jsoup.connect(originLink.trim()).get().title();
        } catch (IOException e) {
            title = "Shorten URL";
        }

        URL url = new URL(account, originLink, linkcode.trim(), title);
        return new Response<>(HttpStatus.CREATED.value(), "Shorten successfully!", urlRepository.save(url));
    }

    public Response<URL> updateLink(HttpServletRequest request, String shortenLink, String linkcode, String title) {
        // Check whether url exists or not
        URL url = urlRepository.findByShortenLink(shortenLink.trim());
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Check if only creator can update link
        int currentAccountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        if (url.getAccount().getId() != currentAccountId) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Only creator can update link");
        }

        // Update linkcode
        if (linkcode != null && linkcode.trim().length() > 0 && !shortenLink.trim().equals(linkcode.trim())) {
            // Check if shorten link exist
            boolean isShortenLinkExist = urlRepository.findByShortenLink(linkcode.trim()) != null;
            if (isShortenLinkExist) {
                return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
            }

            // Update
            url.setShortenLink(linkcode.trim());
        }

        if (title != null || title.trim().length() > 0) {
            url.setTitle(title.trim());
        }

        url.setUpdatedAt(LocalDateTime.now());
        URL updatedUrl = urlRepository.save(url);
        return new Response<>(HttpStatus.OK.value(), "The link is updated successfully", updatedUrl);
    }

    public Response<Void> deleteLink(HttpServletRequest request, String shortenLink) {
        URL url = urlRepository.findByShortenLink(shortenLink.trim());

        // Check whether url exists or not
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Check if only creator can update link
        int currentAccountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        if (url.getAccount().getId() != currentAccountId) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Only creator can delete link");
        }

        // Delete link
        urlRepository.delete(url);
        return new Response<>(HttpStatus.OK.value(), "The link is deleted successfully");
    }

    public Response<URL> shortenLinkWithoutLogin(String originLink, String linkcode) {
        Account NO_ACCOUNT = null;
        return shorten(originLink, linkcode, NO_ACCOUNT);
    }
}