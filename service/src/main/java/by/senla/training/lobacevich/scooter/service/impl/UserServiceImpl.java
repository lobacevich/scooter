package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.UserException;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.entity.ERole;
import by.senla.training.lobacevich.scooter.mapper.UserMapper;
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

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDto createUser(SignupRequest userIn) throws UserException {
        User user = new User();
        user.setUsername(userIn.getUsername());
        user.setFirstname(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setEmail(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setRole(ERole.ROLE_USER);
        try {
            return userMapper.userToUserDto(userRepository.save(user));
        } catch (Exception e) {
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserException("Unable to create user");
        }
    }

    @Override
    public UserDto getCurrentUser(Principal principal) throws UserException {
        return userMapper.userToUserDto(getUserByPrincipal(principal));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Principal principal) throws UserException {
        User user = getUserByPrincipal(principal);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        return userMapper.userToUserDto(userRepository.save(user));
    }

    private User getUserByPrincipal(Principal principal) throws UserException {
        String username = principal.getName();
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserException("User not found with username " + username));
    }
}
