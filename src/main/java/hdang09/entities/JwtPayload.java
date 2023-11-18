package hdang09.entities;

import hdang09.constants.Role;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtPayload {
    public int id;
    public String email;
    public int role;

    public JwtPayload(int id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role.getRole();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("payload", this);
        return map;
    }
}
