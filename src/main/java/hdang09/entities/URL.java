package hdang09.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private int id;

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("origin_link")
    private String originLink;

    @JsonProperty("shorten_link")
    private String shortenLink;

    private int clicks;
    private Date createdAt;

//    @ManyToOne
//    @JoinColumn(name = "account_id", referencedColumnName = "id") // Use consistent logical column name
//    private Account account;


    public URL(int accountId, String originLink, String shortenLink) {
        this.accountId = accountId;
        this.originLink = originLink;
        this.shortenLink = shortenLink;
        this.clicks = 0;
        this.createdAt = new Date();
    }
}
