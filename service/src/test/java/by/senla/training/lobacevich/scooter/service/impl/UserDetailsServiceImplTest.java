package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.entity.User;
import by.senla.training.lobacevich.scooter.repository.UserRepository;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    public static final String USERNAME = "ivan21";
    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void UserDetailsServiceImpl_loadUserByUsername() throws UsernameNotFoundException {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(user.getUsername()).thenReturn(USERNAME);

        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        assertNotNull(userDetails);
        assertEquals(USERNAME, userDetails.getUsername());
    }

    @Test
    public void UserDetailsServiceImpl_loadUserByUsername_UsernameNotFoundException() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(USERNAME),
        "User with username " + USERNAME + " is not found");
    }
}
