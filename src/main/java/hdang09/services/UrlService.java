package hdang09.services;

import hdang09.entities.URL;
import hdang09.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class UrlService {

    @Autowired
    UrlRepository repo;
    final String URL_HOST = "http://localhost:8080";

    public ResponseEntity<Void> redirect(String linkcode) {
        String shortenLink = URL_HOST  + "/" + linkcode;
        URL url = repo.findByShortenLink(shortenLink);
        if (url == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Increase clicks
        int clicks = url.getClicks() + 1;
        url.setClicks(clicks);
        repo.save(url);

        // Redirect
        URI uri = URI.create(url.getOriginLink());
        return ResponseEntity.status(HttpStatus.FOUND).location(uri).build();
    }
}
