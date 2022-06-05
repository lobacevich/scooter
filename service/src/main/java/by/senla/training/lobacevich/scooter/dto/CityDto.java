package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CityDto {

    private Long id;
    @NotBlank(message = "City name is required")
    private String name;
}
