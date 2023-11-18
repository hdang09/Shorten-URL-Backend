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

import java.net.URI;

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
        URL url = urlRepository.findByShortenLink(linkcode.trim());

        // Check whether url exists or not
        if (url == null) {
            final String NOT_FOUND_PAGE = URL_CLIENT + "/404";
            URI uri = URI.create(NOT_FOUND_PAGE);
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

        // Check duplicate origin link in the account
        URL duplicateUrl = urlRepository.checkDuplicate(originLink.trim(), accountId);
        if (duplicateUrl != null) {
            return new Response<>(HttpStatus.OK.value(), "The link created before", duplicateUrl);
        }

        // Check if shorten link exist
        boolean isLinkcodeExist = urlRepository.findByShortenLink(linkcode.trim()) != null;
        if (isLinkcodeExist) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
        }

        // Shorten link
        URL url = new URL(account, originLink, linkcode.trim());
        return new Response<>(HttpStatus.CREATED.value(), "Shorten successfully!", urlRepository.save(url));
    }

    public Response<URL> updateLink(HttpServletRequest request, String shortenLink, String linkcode) {
        // Check whether url exists or not
        URL url = urlRepository.findByShortenLink(shortenLink);
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Check if only creator can update link
        int currentAccountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        if (url.getAccount().getId() != currentAccountId) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Only creator can update link");
        }

        // Check if shorten link exist
        boolean isShortenLinkExist = urlRepository.findByShortenLink(linkcode.trim()) != null;
        if (isShortenLinkExist) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
        }

        // Update link
        url.setShortenLink(linkcode.trim());
        URL updatedUrl = urlRepository.save(url);
        return new Response<>(HttpStatus.OK.value(), "The link is updated successfully", updatedUrl);
    }

    public Response<Void> deleteLink(HttpServletRequest request, String shortenLink) {
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url exists or not
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Check if only creator can update link
        int currentAccountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        if (url.getAccount().getId() != currentAccountId) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Only creator can update link");
        }

        // Delete link
        urlRepository.delete(url);
        return new Response<>(HttpStatus.OK.value(), "The link is deleted successfully");
    }
}