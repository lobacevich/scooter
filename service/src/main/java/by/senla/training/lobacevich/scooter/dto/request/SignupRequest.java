package by.senla.training.lobacevich.scooter.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignupRequest {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Name is required")
    private String firstname;
    @NotBlank(message = "Lastname is required")
    private String lastname;
    @NotBlank(message = "User email is required")
    @Email(message = "It should have email format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
