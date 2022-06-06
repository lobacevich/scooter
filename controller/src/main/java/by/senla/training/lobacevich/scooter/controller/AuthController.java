package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.dto.request.LoginRequest;
import by.senla.training.lobacevich.scooter.dto.request.SignupRequest;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.security.JWTTokenProvider;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    public static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final ValidationErrorResponse validationErrorResponse;

    @PostMapping("/signup")
    public Object registerUser(@Valid @RequestBody SignupRequest signupRequest,
                               BindingResult bindingResult) throws CreationException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        userService.createUser(signupRequest);
        return new MessageResponse("User was register successfully!");
    }
    
    @PostMapping("/login")
    public Object authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new MessageResponse(TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication));
    }
}
