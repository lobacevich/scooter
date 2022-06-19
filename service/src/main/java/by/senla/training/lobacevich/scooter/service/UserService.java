package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UpdateException;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.User;

import java.security.Principal;

public interface UserService {

    User findUserById(Long id) throws NotFoundException;

    UserDto createUser(SignupRequest userIn) throws CreationException;

    UserDto updateUser(UserDto userDto, Principal principal) throws NotFoundException, UpdateException;

    UserDto getCurrentUser(Principal principal) throws NotFoundException;

    User getUserByPrincipal(Principal principal) throws NotFoundException;

    MessageResponse giveDiscountCard(Integer discount, Long userId) throws NotFoundException;
}
