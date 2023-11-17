package hdang09.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private int code;
    private String message;
}
