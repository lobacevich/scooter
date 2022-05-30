package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.dto.LoginRequest;
import by.senla.training.lobacevich.scooter.dto.SignupRequest;
import by.senla.training.lobacevich.scooter.security.JWTTokenProvider;
import by.senla.training.lobacevich.scooter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public String registerUser(@RequestBody SignupRequest signupRequest) {
        userService.createUser(signupRequest);
        return "User registered successfully!";
    }
    
    @PostMapping("/login")
    public String authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
    }
}
