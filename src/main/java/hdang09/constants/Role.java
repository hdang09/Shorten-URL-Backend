package hdang09.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN(0),
    USER(1);

    private final int role;

    @Override
    public String toString() {
        return String.valueOf(role);
    }
}
