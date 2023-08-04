package hdang09.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int id;
    private int accountId;
    private String originLink;
    private String shortenLink;
    private int clicks;
    private Date createdAt;

}
