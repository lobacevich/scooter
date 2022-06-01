package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.UserException;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.entity.User;

import java.security.Principal;

public interface UserService {

    User findUserById(Long id);
    UserDto createUser(SignupRequest userIn) throws UserException;
    UserDto updateUser(UserDto userDto, Principal principal) throws UserException;
    UserDto getCurrentUser(Principal principal) throws UserException;
}
