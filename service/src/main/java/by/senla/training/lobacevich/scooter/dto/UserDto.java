package by.senla.training.lobacevich.scooter.dto;

import by.senla.training.lobacevich.scooter.entity.enums.ERole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private ERole role;
}
