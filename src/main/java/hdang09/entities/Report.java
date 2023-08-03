package hdang09.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Report {

    @Id
    private int id;
    private int accountId;
    private int totalClicks;
    private int totalLinks;
//    private List<URL> links;

    public Report() {
    }

    public Report(int id, int accountId, int totalClicks, int totalLinks) {
        this.id = id;
        this.accountId = accountId;
        this.totalClicks = totalClicks;
        this.totalLinks = totalLinks;
//        this.links = links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
    }

    public int getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
    }

//    public List<URL> getLinks() {
//        return links;
//    }
//
//    public void setLinks(List<URL> links) {
//        this.links = links;
//    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", totalClicks=" + totalClicks +
                ", totalLinks=" + totalLinks +
//                ", links=" + links +
                '}';
    }
}
