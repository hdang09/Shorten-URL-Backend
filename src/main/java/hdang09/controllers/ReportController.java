package hdang09.controllers;

import hdang09.entities.Report;
import hdang09.entities.Response;
import hdang09.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Report")
@RequestMapping("/api/report")
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    @Autowired
    ReportService service;

    @Operation(summary = "Get a report total click and link of a user")
    @GetMapping("{accountId}")
    public Response<Report> getByAccountId(
            @RequestHeader(value = "token") String token,
            @PathVariable("accountId") int accountId
    ) {
        return service.getByAccountId(accountId);
    }

    @Operation(summary = "Get a report total click and link of a user")
    @GetMapping("{accountId}/{year}/{month}")
    public Response<Report> getByAccountIdAndDate(
            @RequestHeader(value = "token") String token,
            @PathVariable("accountId") int accountId,
            @PathVariable("year") int year,
            @PathVariable("month") int month

    ) {
        return service.getByAccountIdAndDate(accountId, year, month);
    }
}
