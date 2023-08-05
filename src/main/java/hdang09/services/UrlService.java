package hdang09.services;

import hdang09.entities.Account;
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

    public URL shortenLink(String originLink, int accountId, String linkcode) {
        String shortenLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        Account account = accountRepository.getById(accountId);

        // Check whether account exists or not
        if (account == null) {
            // TODO: Notify account not found
            return null;
        }

        // Shorten link
        URL url = new URL(accountId, originLink, shortenLink);
        return urlRepository.save(url);
    }

    public URL updateLink(String shortenLink, String linkcode) {
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url exists or not
        if (url == null) {
            // TODO: Notify url not found
            return null;
        }

        // Update link
        String newShortenLink = UrlUtil.getBaseUrl() + "/" + linkcode;
        url.setShortenLink(newShortenLink);
        return urlRepository.save(url);
    }

    public void deleteLink(String shortenLink) {
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url exists or not
        if (url == null) {
            // TODO: Notify url not found
            return;
        }

        // Delete link
        urlRepository.delete(url);
    }
}