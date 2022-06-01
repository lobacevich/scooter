package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.UserException;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(Principal principal) throws UserException {
        return userService.getCurrentUser(principal);
    }

    @PostMapping("/update")
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) throws UserException {
        return userService.updateUser(userDto, principal);
    }
}
