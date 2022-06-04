package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UserException;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.entity.User;

import java.security.Principal;

public interface UserService {

    User findUserById(Long id) throws NotFoundException;

    UserDto createUser(SignupRequest userIn) throws UserException;

    UserDto updateUser(UserDto userDto, Principal principal) throws NotFoundException;

    UserDto getCurrentUser(Principal principal) throws NotFoundException;

    User getUserByPrincipal(Principal principal) throws NotFoundException;

    MessageResponse giveDiscountCard(int discount, Long userId) throws NotFoundException;

    MessageResponse byuSeasonTicket(ScooterModel model, Principal principal) throws NotFoundException;
}
