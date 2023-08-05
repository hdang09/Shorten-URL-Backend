package hdang09.entities;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Response<T> ok(String message, T data) {
        return new Response<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> Response<T> notFound(String message, T data) {
        return new Response<>(HttpStatus.NOT_FOUND.value(), message, data);
    }
}
