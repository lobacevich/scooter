package by.senla.training.lobacevich.scooter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CityDto {

    private Long id;
    @NotBlank(message = "City name is required")
    private String name;
}
