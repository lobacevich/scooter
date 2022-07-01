package by.senla.training.lobacevich.scooter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
public class DiscountCardDto {

    private Long id;
    @Min(value = 0, message = "Discount can not be less than 0")
    private Integer discountPercent;
    private BigDecimal totalSum;
}
