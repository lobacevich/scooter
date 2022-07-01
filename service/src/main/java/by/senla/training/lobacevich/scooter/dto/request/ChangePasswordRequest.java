package by.senla.training.lobacevich.scooter.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
