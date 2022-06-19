package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UpdateException;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public User findUserById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id=" + id + " is not found"));
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
            log.error("Error during user registration. {}", e.getMessage());
            throw new CreationException("User with the same username or email already exists");
        }
    }

    @Override
    public UserDto getCurrentUser(Principal principal) throws NotFoundException {
        return userMapper.userToDto(getUserByPrincipal(principal));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Principal principal) throws NotFoundException, UpdateException {
        User user = getUserByPrincipal(principal);
        user.setFirstname(userDto.getFirstname() != null ? userDto.getFirstname() : user.getFirstname());
        user.setLastname(userDto.getLastname() != null ? userDto.getLastname() : user.getLastname());
        user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail());
        try {
            return userMapper.userToDto(userRepository.save(user));
        } catch (Exception e) {
            log.error("Error during user update. {}", e.getMessage());
            throw new UpdateException("User with the same email already exists");
        }
    }

    @Override
    public User getUserByPrincipal(Principal principal) throws NotFoundException {
        String username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException("User not found with username " + username));
    }

    @Override
    public MessageResponse giveDiscountCard(Integer discount, Long userId) throws NotFoundException {
        User user = findUserById(userId);
        user.setDiscountCard(discountCardRepository.save(new DiscountCard(discount)));
        userRepository.save(user);
        return new MessageResponse("Discount card was add successfully");
    }
}
