package hdang09.controllers;

import hdang09.entities.Response;
import hdang09.entities.URL;
import hdang09.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "URL")
@RequestMapping("/api/url/shorten")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class UrlController {

    @Autowired
    UrlService service;

    @Operation(summary = "Make the link shorten")
    @PostMapping()
    public Response<URL> shortenLink(
            HttpServletRequest request,
            @RequestParam("originLink") String originLink,
            @RequestParam("linkcode") String linkcode
    ) {
        return service.shortenLink(request, originLink, linkcode);
    }

    @Operation(summary = "Edit the shorten link")
    @PutMapping("/update-link")
    public Response<URL> updateLink(
            HttpServletRequest request,
            @RequestParam("shortenLink") String shortenLink,
            @RequestParam(name = "linkcode", required = false) String linkcode,
            @RequestParam(name = "title", required = false) String title
    ) {
        return service.updateLink(request, shortenLink, linkcode, title);
    }

    @Operation(summary = "Delete the shorten link")
    @DeleteMapping("/delete-link")
    public Response<Void> deleteLink(HttpServletRequest request, @RequestParam("shortenLink") String shortenLink) {
        return service.deleteLink(request, shortenLink);
    }

}
