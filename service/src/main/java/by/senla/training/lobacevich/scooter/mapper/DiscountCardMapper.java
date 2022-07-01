package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.DiscountCardDto;
import by.senla.training.lobacevich.scooter.entity.DiscountCard;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardMapper {

    public DiscountCardDto discountCardToDto(DiscountCard card) {
        return DiscountCardDto.builder()
                .id(card.getId())
                .discountPercent(card.getDiscountPercent())
                .totalSum(card.getTotalSum())
                .build();
    }
}
