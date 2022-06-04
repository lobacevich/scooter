package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TariffDto {

    private Long id;
    @NotNull(message = "Model id is required")
    private Long modelId;
    @NotNull(message = "Price is required")
    private BigDecimal pricePerHour;
}
