package hdang09.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
