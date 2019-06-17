package com.github.aleksanderkot00.onlinesportsbetting.reposiotory;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
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
        user2.setBalance(new BigDecimal("100.99"));
        user2.setEncryptedPassword("Password123");
        user2.setRoles(new HashSet<>());
        user2.setActive(true);
        user2.setCartSlip(new Slip());
        user2.getRoles().add(role);

        //When
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();

        //Then
        assertEquals(initialNumberOfUsers + 2, numberOfUsers);
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
        user.setActive(true);
        userRepository.save(user);

        //When
        User foundUser = userRepository.findById(user.getUserId()).orElseThrow(UserNotFoundException::new);

        //Then
        assertEquals(user, foundUser);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDelete() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
        user.setActive(true);
        userRepository.save(user);

        //When
        userRepository.delete(user);
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();

        //Then
        assertEquals(initialNumberOfUsers, numberOfUsers);
        assertFalse(users.contains(user));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
        user.setActive(true);
        userRepository.save(user);

        //When
        userRepository.deleteById(user.getUserId());
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();

        //Then
        assertEquals(initialNumberOfUsers, numberOfUsers);
        assertFalse(users.contains(user));
    }
}