package hdang09.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "url")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "origin_link")
    private String originLink;


    @Column(name = "shorten_link")
    private String shortenLink;


    private int clicks;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Account account;

    public URL(Account account, String originLink, String shortenLink) {
        this.account = account;
        this.originLink = originLink;
        this.shortenLink = shortenLink;
        this.clicks = 0;
        this.createdAt = new Date();
    }
}
