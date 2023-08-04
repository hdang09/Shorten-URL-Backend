package hdang09.controllers;

import hdang09.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Tag(name = "URL")
public class UrlController {

    @Autowired
    UrlService service;

    @Operation(summary = "Redirect origin link")
    @GetMapping("/{linkcode}")
    public RedirectView redirect(@PathVariable("linkcode") String linkcode) {
        RedirectView redirectView = new RedirectView();
        String url = service.getOriginLinkByLinkcode(linkcode);
        redirectView.setUrl(url);
        return redirectView;
    }
}
