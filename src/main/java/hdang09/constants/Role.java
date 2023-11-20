package hdang09.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER(0),
    ADMIN(1);

    private final int role;

    public static Role fromInt(int value) {
        for (Role role : Role.values()) {
            if (role.getRole() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value);
    }
}
