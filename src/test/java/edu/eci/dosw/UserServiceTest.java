package edu.eci.dosw;

import edu.eci.dosw.core.exception.UserNotFoundException;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // --- EXITOSOS ---

    @Test
    void testRegisterUser() {
        User user = new User(null, "Juan");
        User result = userService.registerUser(user);
        assertNotNull(result.getId());
        assertEquals("Juan", result.getName());
    }

    @Test
    void testGetAllUsers() {
        userService.registerUser(new User(null, "Juan"));
        userService.registerUser(new User(null, "Laura"));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        User user = userService.registerUser(new User(null, "Juan"));
        User found = userService.getUserById(user.getId());
        assertEquals(user.getId(), found.getId());
    }

    // --- DE ERROR ---

    @Test
    void testGetUserByIdNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("id-inexistente"));
    }

    @Test
    void testRegisterUserEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.registerUser(new User(null, "")));
    }
}