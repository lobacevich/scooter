package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UpdateException;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.entity.DiscountCard;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.mapper.UserMapper;
import by.senla.training.lobacevich.scooter.repository.DiscountCardRepository;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    public static final Long ID = 1L;
    public static final String NOT_FOUND_MESSAGE = "User with id=" + ID + " is not found";
    public static final String USERNAME = "Ivan21";
    public static final String FIRSTNAME = "Ivan";
    public static final String NOT_FOUND_PRINCIPAL_MESSAGE = "User not found with username " + USERNAME;
    public static final String ADD_DISCOUNT_CARD = "Discount card was add successfully";
    public static final Integer DISCOUNT = 20;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private User user;
    @Mock
    private UserDto userDto;
    @Mock
    private SignupRequest signupRequest;
    @Mock
    private Principal principal;
    @Mock
    private DiscountCard discountCard;

    @Test
    public void UserServiceImpl_findUserById() throws NotFoundException {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        User resultUser = userService.findUserById(ID);

        assertEquals(user, resultUser);
    }

    @Test
    public void UserServiceImpl_findUserById_NotFoundException() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(ID),
                NOT_FOUND_MESSAGE);
    }

    @Test
    public void UserServiceImpl_createUser() throws CreationException {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto resultUserDto = userService.createUser(signupRequest);

        assertNotNull(resultUserDto);
        assertEquals(userDto, resultUserDto);
    }

    @Test
    public void UserServiceImpl_getCurrentUser() throws NotFoundException {
        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto resultUserDto = userService.getCurrentUser(principal);

        assertNotNull(resultUserDto);
        assertEquals(userDto, resultUserDto);
    }

    @Test
    public void UserServiceImpl_updateUser() throws NotFoundException, UpdateException {
        User userEntity = new User();
        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userEntity));
        when(userDto.getFirstname()).thenReturn(FIRSTNAME);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.userToDto(userEntity)).thenReturn(userDto);

        UserDto resultUserDto = userService.updateUser(userDto, principal);

        assertNotNull(resultUserDto);
        assertEquals(userDto, resultUserDto);
        assertEquals(FIRSTNAME, userEntity.getFirstname());
    }

    @Test
    public void UserServiceImpl_getUserByPrincipal() throws NotFoundException {
        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        User resultUser = userService.getUserByPrincipal(principal);

        assertNotNull(resultUser);
        assertEquals(user, resultUser);
    }

    @Test
    public void UserServiceImpl_getUserByPrincipal_NotFoundException() {
        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByPrincipal(principal),
                NOT_FOUND_PRINCIPAL_MESSAGE);
    }
}
