package hdang09.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    WAITING("waiting"),
    REJECT("reject"),
    ACCEPT("accept");

    private final String status;

    @Override
    public String toString() {
        return String.valueOf(status).toLowerCase();
    }
}
