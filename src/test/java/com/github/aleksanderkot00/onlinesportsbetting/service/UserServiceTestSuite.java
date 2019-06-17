package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.repository.RoleRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Before
    public void init() {
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        user1.setActive(true);
        Role role = new Role();
        role.setRole("USER");
        user1.setRoles(new HashSet<>());
        user1.setCartSlip(new Slip());
        user1.getRoles().add(role);
        User user2 = new User();
        user2.setName("Test Name2");
        user2.setLastName("Test Lastname2");
        user2.setEmail("email2@test.com");
        user2.setBalance(BigDecimal.ZERO);
        user2.setEncryptedPassword("Password123");
        user2.setRoles(new HashSet<>());
        user2.setActive(true);
        user2.setCartSlip(new Slip());
        user2.getRoles().add(role);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        when(roleRepository.findByRole("USER")).thenReturn(Optional.of(role));
        when(encoder.encode(anyString())).then(returnsFirstArg());
    }

    @Test
    public void testGetUsers() {
        //When
        List<User> users = userService.getUsers();

        //Then
        assertEquals(2, users.size());
        assertEquals("email1@test.com", users.get(0).getEmail());
        assertEquals("Test Name1", users.get(0).getName());
        assertEquals("email2@test.com", users.get(1).getEmail());
        assertEquals("Test Lastname2", users.get(1).getLastName());
        assertEquals(new BigDecimal("1231.11"), users.get(0).getBalance());
        assertEquals(BigDecimal.ZERO, users.get(1).getBalance());
    }

    @Test
    public void testGetUser() {
        //When
        User user = userService.getUser(1312);

        //Then
        assertEquals("email1@test.com", user.getEmail());
        assertEquals("Test Name1", user.getName());
        assertEquals(new BigDecimal("1231.11"), user.getBalance());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.isActive());
    }

    @Test
    public void testAddUser() {
        //Given
        UserRegistrationDto registrationDto = new UserRegistrationDto(
                "Test name",
                "Test lastname",
                "test@email",
                "testpassword"
        );

        //When
        User user = userService.addUser(registrationDto);

        //Then
        assertEquals("test@email", user.getEmail());
        assertEquals("Test name", user.getName());
        assertEquals("Test lastname", user.getLastName());
        assertEquals(BigDecimal.ZERO, user.getBalance());
        assertEquals(encoder.encode("testpassword"), user.getEncryptedPassword());
        assertTrue(user.isActive());
    }

    @Test
    public void testEditUser() {
        //Given
        UserRegistrationDto registrationDto = new UserRegistrationDto(
                "Edited name",
                "",
                "Edited@email",
                null
        );

        //When
        User user = userService.editUser(12, registrationDto);

        //Then
        assertEquals("Edited@email", user.getEmail());
        assertEquals("Edited name", user.getName());
        assertEquals("Test Lastname1", user.getLastName());
        assertEquals(new BigDecimal("1231.11"), user.getBalance());
        assertEquals(encoder.encode("Password23"), user.getEncryptedPassword());
        assertTrue(user.isActive());
    }

    @Test
    public void testDeleteBet() {
        //When
        userService.deleteUser(14l);

        //Then
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testLoadUserByUsername() {
        //When
        UserDetails userDetails = userService.loadUserByUsername("sdasdad");

        //Then
        assertEquals("email1@test.com", userDetails.getUsername());
        assertEquals("Password23", userDetails.getPassword());
    }
}