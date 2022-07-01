package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.DiscountCardDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.DiscountCard;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.mapper.DiscountCardMapper;
import by.senla.training.lobacevich.scooter.repository.DiscountCardRepository;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class DiscountCardServiceImplTest {

    public static final Long ID = 1L;
    public static final Integer PERCENT = 10;
    public static final String DOES_NOT_HAVE_MESSAGE = "User doesn't have a discount card";
    public static final String JUST_HAVE_MESSAGE = "User just have a discount card";
    @Mock
    private User user;
    @Mock
    private Principal principal;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DiscountCard discountCard;
    @Mock
    private DiscountCardDto discountCardDto;
    @Mock
    private DiscountCardMapper discountCardMapper;
    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private DiscountCardServiceImpl discountCardService;

    @Test
    public void DiscountCardServiceImpl_getDiscountCard() throws NotFoundException {
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(discountCard);
        when(discountCardMapper.discountCardToDto(discountCard)).thenReturn(discountCardDto);

        DiscountCardDto resultDiscountCardDto = (DiscountCardDto) discountCardService.getDiscountCard(principal);

        assertNotNull(resultDiscountCardDto);
        assertEquals(discountCardDto, resultDiscountCardDto);
    }

    @Test
    public void DiscountCardServiceImpl_getDiscountCard_doesNotHave() throws NotFoundException {
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(null);

        MessageResponse response = (MessageResponse) discountCardService.getDiscountCard(principal);

        assertEquals(DOES_NOT_HAVE_MESSAGE, response.getMessage());
    }

    @Test
    public void DiscountCardServiceImpl_addDiscountCard() throws NotFoundException {
        when(userService.findUserById(ID)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(null);
        when(discountCardRepository.save(any(DiscountCard.class))).thenReturn(discountCard);
        when(discountCardMapper.discountCardToDto(discountCard)).thenReturn(discountCardDto);

        DiscountCardDto resultDiscountCardDto = (DiscountCardDto) discountCardService.addDiscountCard(discountCardDto, ID);

        assertNotNull(resultDiscountCardDto);
        assertEquals(discountCardDto, resultDiscountCardDto);
    }

    @Test
    public void DiscountCardServiceImpl_addDiscountCard_justHave() throws NotFoundException {
        when(userService.findUserById(ID)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(discountCard);

        MessageResponse response = (MessageResponse) discountCardService.addDiscountCard(discountCardDto, ID);

        assertEquals(JUST_HAVE_MESSAGE, response.getMessage());
    }

    @Test
    public void DiscountCardServiceImpl_updateDiscountCard() throws NotFoundException {
        DiscountCard discountCardEntity = new DiscountCard();
        when(userService.findUserById(ID)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(discountCardEntity);
        when(discountCardDto.getDiscountPercent()).thenReturn(PERCENT);
        when(discountCardRepository.save(discountCardEntity)).thenReturn(discountCardEntity);
        when(discountCardMapper.discountCardToDto(discountCardEntity)).thenReturn(discountCardDto);

        DiscountCardDto resultDiscountCardDto = (DiscountCardDto) discountCardService.updateDiscountCard(discountCardDto, ID);

        assertEquals(discountCardDto, resultDiscountCardDto);
        assertEquals(PERCENT, discountCardEntity.getDiscountPercent());
    }

    @Test
    public void DiscountCardServiceImpl_updateDiscountCard_doesNotHave() throws NotFoundException {
        when(userService.findUserById(ID)).thenReturn(user);
        when(user.getDiscountCard()).thenReturn(null);

        MessageResponse response = (MessageResponse) discountCardService.updateDiscountCard(discountCardDto, ID);

        assertEquals(DOES_NOT_HAVE_MESSAGE, response.getMessage());
    }
}
