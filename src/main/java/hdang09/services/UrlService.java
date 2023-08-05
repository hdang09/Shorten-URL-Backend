package hdang09.services;

import hdang09.entities.Account;
import hdang09.entities.URL;
import hdang09.repositories.AccountRepository;
import hdang09.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlService {

    @Autowired
    UrlRepository urlRepository;
    @Autowired
    AccountRepository accountRepository;

    final String URL_HOST = "http://localhost:8080";

    public ResponseEntity<Void> redirect(String linkcode) {
        String shortenLink = URL_HOST  + "/" + linkcode;
        URL url = urlRepository.findByShortenLink(shortenLink);

        // Check whether url is existed or not
        if (url == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        String shortenLink = URL_HOST  + "/" + linkcode;
        Account acc = accountRepository.getById(accountId);

        // Check whether account is existed or not
        if (acc == null) {
            // TODO: Notify account not found
            return null;
        }

        // Shorten link
        URL url = new URL(accountId, originLink, shortenLink);
        return urlRepository.save(url);
    }
}
