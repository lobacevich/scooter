package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.dto.SignupRequest;
import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.entity.ERole;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setUsername(userIn.getUsername());
        user.setFirstname(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setEmail(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        return userRepository.save(user);
    }
}
