package hdang09.controllers;

import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "URL")
@RequestMapping("/api/url")
@CrossOrigin
public class UrlController {

    @Autowired
    UrlService service;

    @Operation(summary = "Make the link shorten")
    @PostMapping("/shorten")
    public Response<URL> shortenLink(
            @RequestHeader(name = "token", required = false) String token,
            @RequestParam("originLink") String originLink,
            @RequestParam("accountId") int accountId,
            @RequestParam("linkcode") String linkcode
    ) {
        return service.shortenLink(originLink, accountId, linkcode);
    }

    // TODO: Consider changing to PutMapping
    @Operation(summary = "Edit the shorten link")
    @PostMapping("/update-link")
    public Response<URL> updateLink(
            @RequestHeader(name = "token", required = false) String token,
            @RequestParam("shortenLink") String shortenLink,
            @RequestParam("linkcode") String linkcode
    ) {
        return service.updateLink(shortenLink, linkcode);
    }

    @Operation(summary = "Delete the shorten link")
    @DeleteMapping("/delete-link")
    public Response<Void> deleteLink(
            @RequestHeader(name = "token", required = false) String token,
            @RequestParam("shortenLink") String shortenLink
    ) {
        return service.deleteLink(shortenLink);
    }

}
