package hdang09.entities;

import hdang09.constants.Role;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class JwtPayload {
    // TODO: Fix 'String' type of _id and role (Add @AllArgsCon...)
    public String _id;
    public String email;
    public int role;

    public JwtPayload(int _id, String email, Role role) {
        this._id = String.valueOf(_id);
        this.email = email;
        this.role = role.getRole();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("payload", this);
        return map;
    }
}
