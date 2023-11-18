package hdang09.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private int accountId;
    private Long totalClicks;
    private Long totalLinks;
    private List<URL> links;

    public Report(int accountId, Long totalClicks, Long totalLinks) {
        this.accountId = accountId;
        this.totalClicks = totalClicks;
        this.totalLinks = totalLinks;
    }

    public Report(int accountId) {
        this.accountId = accountId;
        this.totalClicks = 0L;
        this.totalLinks = 0L;
        this.links = new ArrayList<>();
    }
}
