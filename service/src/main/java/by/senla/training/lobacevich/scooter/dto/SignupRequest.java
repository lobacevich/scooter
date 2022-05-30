package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
