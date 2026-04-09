package edu.eci.dosw;

import edu.eci.dosw.core.exception.UserNotFoundException;
import edu.eci.dosw.core.model.User;
import edu.eci.dosw.core.service.UserService;
import edu.eci.dosw.persistence.relational.entity.Role;
import edu.eci.dosw.persistence.relational.entity.UserEntity;
import edu.eci.dosw.persistence.relational.mapper.UserPersistenceMapper;
import edu.eci.dosw.persistence.relational.repository.UserRepository;
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
    private UserRepository userRepository;
    private UserPersistenceMapper userMapper;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userMapper = Mockito.mock(UserPersistenceMapper.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, userMapper, passwordEncoder);
    }

    @Test
    void testRegisterUser() {
        User user = new User(null, "Juan", "juanito", "pass123", Role.USER);
        UserEntity entity = new UserEntity(null, "Juan", "juanito", "pass123", Role.USER);
        User saved = new User("uuid-1", "Juan", "juanito", "pass123", Role.USER);

        when(userMapper.toEntity(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toModel(entity)).thenReturn(saved);

        User result = userService.registerUser(user);
        assertNotNull(result.getId());
        assertEquals("Juan", result.getName());
    }

    @Test
    void testGetAllUsers() {
        UserEntity e1 = new UserEntity("1", "Juan", "juanito", "pass", Role.USER);
        UserEntity e2 = new UserEntity("2", "Laura", "laurita", "pass", Role.USER);
        when(userRepository.findAll()).thenReturn(List.of(e1, e2));
        when(userMapper.toModel(e1)).thenReturn(new User("1", "Juan", "juanito", "pass", Role.USER));
        when(userMapper.toModel(e2)).thenReturn(new User("2", "Laura", "laurita", "pass", Role.USER));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testGetUserById() {
        UserEntity entity = new UserEntity("uuid-1", "Juan", "juanito", "pass", Role.USER);
        User user = new User("uuid-1", "Juan", "juanito", "pass", Role.USER);
        when(userRepository.findById("uuid-1")).thenReturn(Optional.of(entity));
        when(userMapper.toModel(entity)).thenReturn(user);

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