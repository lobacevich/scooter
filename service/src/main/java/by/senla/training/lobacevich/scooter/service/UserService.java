package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.dto.SignupRequest;
import by.senla.training.lobacevich.scooter.entity.User;

public interface UserService {

    User findUserById(Long id);
    User createUser(SignupRequest userIn);
}
