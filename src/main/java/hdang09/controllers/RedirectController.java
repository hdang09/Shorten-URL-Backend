package hdang09.controllers;

import hdang09.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@Tag(name = "Redirect")
public class RedirectController {

    @Autowired
    UrlService service;

    @GetMapping()
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @Operation(summary = "Redirect origin link")
    @GetMapping("/{linkcode}")
    public ResponseEntity<Void> redirect(@PathVariable("linkcode") String linkcode) {
        return service.redirect(linkcode);
    }
}
