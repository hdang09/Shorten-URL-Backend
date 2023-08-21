package hdang09.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import hdang09.constants.Role;
import hdang09.constants.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "_id", access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Role role = Role.USER;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACCEPT; // Default value for status: ACCEPT

    // TODO: If front-end runs without bug, remove the line below
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String avatar;

//    @OneToMany(mappedBy = "account")
//    private List<URL> urls;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    public String getStatusValue() {
        return this.status == null ? Status.WAITING.getStatus() : this.status.getStatus();
    }

    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    public int getRoleValue() {
        return this.role == null ? Role.USER.getRole() : this.role.getRole();
    }

    public Account(String firstName, String lastName, String email, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
    }
}
