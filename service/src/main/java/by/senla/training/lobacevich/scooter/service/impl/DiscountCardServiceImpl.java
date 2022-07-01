package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.DiscountCardDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.DiscountCard;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.mapper.DiscountCardMapper;
import by.senla.training.lobacevich.scooter.repository.DiscountCardRepository;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import by.senla.training.lobacevich.scooter.service.DiscountCardService;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
@AllArgsConstructor
@Log4j2
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardMapper discountCardMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public Object getDiscountCard(Principal principal) throws NotFoundException {
        User user = userService.getUserByPrincipal(principal);
        if (user.getDiscountCard() == null) {
            return new MessageResponse("User doesn't have a discount card");
        }
        return discountCardMapper.discountCardToDto(user.getDiscountCard());
    }

    @Override
    public Object addDiscountCard(DiscountCardDto discountCardDto, Long userId) throws NotFoundException {
        User user = userService.findUserById(userId);
        if (user.getDiscountCard() != null) {
            return new MessageResponse("User just have a discount card");
        }
        DiscountCard card = new DiscountCard(discountCardDto.getDiscountPercent());
        card.setTotalSum(BigDecimal.ZERO);
        card = discountCardRepository.save(card);
        user.setDiscountCard(card);
        userRepository.save(user);
        log.info("A {} percent discount is provided to the user {}", card.getDiscountPercent(),
                user.getUsername());
        return discountCardMapper.discountCardToDto(card);
    }

    @Override
    public Object updateDiscountCard(DiscountCardDto discountCardDto, Long userId) throws NotFoundException {
        User user = userService.findUserById(userId);
        DiscountCard card = user.getDiscountCard();
        if (card == null) {
            return new MessageResponse("User doesn't have a discount card");
        } else {
            card.setDiscountPercent(discountCardDto.getDiscountPercent());
            userRepository.save(user);
            log.info("User {}'s discount has been changed to {} percent", user.getUsername(),
                    card.getDiscountPercent());
            return discountCardMapper.discountCardToDto(discountCardRepository.save(card));
        }
    }
}
