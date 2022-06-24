package by.senla.training.lobacevich.scooter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PointDto {

    private Long id;
    @NotNull(message = "City id is required")
    private Long cityId;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Pone number is required")
    private String phoneNumber;
    @NotNull(message = "Latitude is required")
    private Integer latitude;
    @NotNull(message = "Longitude is required")
    private Integer longitude;
    private Double distance;
}
