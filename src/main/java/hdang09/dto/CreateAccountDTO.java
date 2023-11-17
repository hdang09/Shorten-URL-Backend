package hdang09.dto;

import hdang09.constants.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
    private Role role = Role.USER;

    @Email(message = "Please enter a valid email")
    @Schema(example = "example@gmail.com", description = "Email of an account")
    private String email;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(example = "Example", description = "First name of an account")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(example = "Example", description = "Last name of an account")
    private String lastName;
}
