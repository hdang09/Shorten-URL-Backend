package hdang09.services;

import hdang09.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    UrlRepository repo;
    public String getOriginLinkByLinkcode(String linkcode) {
        final String URL_HOST = "http://localhost:8080";
        String shortenLink = URL_HOST + linkcode;
        return repo.getOriginLinkByLinkcode(shortenLink);
    }
}
