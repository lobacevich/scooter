package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.DiscountCardDto;

import java.security.Principal;

public interface DiscountCardService {

    Object getDiscountCard(Principal principal) throws NotFoundException;

    Object addDiscountCard(DiscountCardDto discountCardDto, Long userId) throws NotFoundException;

    Object updateDiscountCard(DiscountCardDto discountCardDto, Long userId) throws NotFoundException;
}
