package edu.eci.dosw;

import edu.eci.dosw.core.exception.UserNotFoundException;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.core.service.UserService;
import edu.eci.dosw.persistence.IUserRepository;
import edu.eci.dosw.persistence.relational.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserService userService;
    private IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUser() {
        User user = new User(null, "Juan", "juanito", "pass123", Role.USER);
        User saved = new User("uuid-1", "Juan", "juanito", "encoded", Role.USER);
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(saved);
        User result = userService.registerUser(user);
        assertNotNull(result.getId());
        assertEquals("Juan", result.getName());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(
                new User("1", "Juan", "juanito", "pass", Role.USER),
                new User("2", "Laura", "laurita", "pass", Role.USER)
        ));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User user = new User("uuid-1", "Juan", "juanito", "pass", Role.USER);
        when(userRepository.findById("uuid-1")).thenReturn(Optional.of(user));
        User found = userService.getUserById("uuid-1");
        assertEquals("uuid-1", found.getId());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById("no-existe")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("no-existe"));
    }

    @Test
    void testRegisterUserEmptyName() {
        User user = new User(null, "", "juanito", "pass", Role.USER);
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }
}