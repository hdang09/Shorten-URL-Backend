package hdang09.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "title")
    private String title;

    @Column(name = "origin_link")
    private String originLink;

    @Column(name = "shorten_link")
    private String shortenLink;

    private int clicks;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Account account;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt = null;

    public URL(Account account, String originLink, String shortenLink, String title) {
        final long MAX_EXPIRED_DAY = 7;

        this.account = account;
        this.originLink = originLink;
        this.shortenLink = shortenLink;
        this.clicks = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.title = title;
        this.expiredAt = account == null ? LocalDateTime.now().plusDays(MAX_EXPIRED_DAY) : null;
    }
}
