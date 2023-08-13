package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import hdang09.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository, AccountRepository accountRepository) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Void> redirect(String linkcode) {
        String shortenLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url exists or not
        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        // Increase clicks
        int clicks = url.getClicks() + 1;
        url.setClicks(clicks);
        urlRepository.save(url);

        // Redirect
        URI uri = URI.create(url.getOriginLink());
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }

    public Response<URL> shortenLink(String originLink, int accountId, String linkcode) {
        // TODO: Validate originLink, linkcode (.trim())

        // Check whether account exists or not
        Account account = accountRepository.getById(accountId);
        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account not found");
        }

        // Check duplicate origin link in the account
        URL duplicateUrl = urlRepository.checkDuplicate(originLink, accountId);
        if (duplicateUrl != null) {
            return new Response<>(HttpStatus.OK.value(), "The link created before", duplicateUrl);
        }

        // Check if shorten link exist
        String shortenLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        boolean isLinkcodeExist = urlRepository.findByShortenLink(shortenLink) != null;
        if (isLinkcodeExist) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
        }

        // Shorten link
        URL url = new URL(accountId, originLink, shortenLink);
        return new Response<>(HttpStatus.CREATED.value(), urlRepository.save(url));
    }

    public Response<URL> updateLink(String shortenLink, String linkcode) {
        // Check whether url exists or not
        URL url = urlRepository.findByShortenLink(shortenLink);
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Check if shorten link exist
        String newShortLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        boolean isLinkcodeExist = urlRepository.findByShortenLink(newShortLink) != null;
        if (isLinkcodeExist) {
            return new Response<>(HttpStatus.FORBIDDEN.value(), "Link code is exist");
        }

        // Update link
        String newShortenLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        url.setShortenLink(newShortenLink);
        URL updatedUrl = urlRepository.save(url);
        return new Response<>(HttpStatus.OK.value(), "The link is updated successfully", updatedUrl);
    }

    public Response<Void> deleteLink(String shortenLink) {
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url exists or not
        if (url == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Link not found");
        }

        // Delete link
        urlRepository.delete(url);
        return new Response<>(HttpStatus.OK.value(), "The link is deleted successfully");
    }
}