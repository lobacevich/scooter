package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.DiscountCard;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.entity.enums.ERole;
import by.senla.training.lobacevich.scooter.mapper.UserMapper;
import by.senla.training.lobacevich.scooter.repository.DiscountCardRepository;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public User findUserById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect user id"));
    }

    @Override
    public UserDto createUser(SignupRequest userIn) throws CreationException {
        User user = new User();
        user.setUsername(userIn.getUsername());
        user.setFirstname(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setEmail(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setRole(ERole.ROLE_USER);
        try {
            return userMapper.userToDto(userRepository.save(user));
        } catch (Exception e) {
            LOG.error("Error during user registration. {}", e.getMessage());
            throw new CreationException("User with the same username or email already exists");
        }
    }

    @Override
    public UserDto getCurrentUser(Principal principal) throws NotFoundException {
        return userMapper.userToDto(getUserByPrincipal(principal));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Principal principal) throws NotFoundException {
        User user = getUserByPrincipal(principal);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        return userMapper.userToDto(userRepository.save(user));
    }

    @Override
    public User getUserByPrincipal(Principal principal) throws NotFoundException {
        String username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("User not found with username " + username));
    }

    @Override
    public MessageResponse giveDiscountCard(Integer discount, Long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Incorrect user's id"));
        user.setDiscountCard(discountCardRepository.save(new DiscountCard(discount)));
        userRepository.save(user);
        return new MessageResponse("Discount card was added successfully");
    }
}
