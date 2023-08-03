package hdang09.entities;

import java.util.Date;

public class URL {
    private int id;
    private int accountId;
    private String originLink;
    private String shortenLink;
    private int clicks;
    private Date createdAt;

    public URL() {
    }

    public URL(int id, int accountId, String originLink, String shortenLink, int clicks, Date createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.originLink = originLink;
        this.shortenLink = shortenLink;
        this.clicks = clicks;
        this.createdAt = createdAt;
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

    public String getOriginLink() {
        return originLink;
    }

    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    public String getShortenLink() {
        return shortenLink;
    }

    public void setShortenLink(String shortenLink) {
        this.shortenLink = shortenLink;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "URL{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", originLink='" + originLink + '\'' +
                ", shortenLink='" + shortenLink + '\'' +
                ", clicks=" + clicks +
                ", createdAt=" + createdAt +
                '}';
    }
}
