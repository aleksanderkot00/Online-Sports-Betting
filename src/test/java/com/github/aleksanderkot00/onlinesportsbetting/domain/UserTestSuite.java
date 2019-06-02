package com.github.aleksanderkot00.onlinesportsbetting.domain;

import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void SaveAndFindAll() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        User user2 = new User();
        user2.setName("Test Name2");
        user2.setLastName("Test Lastname2");
        user2.setEmail("email2@test.com");
        user2.setBalance(new BigDecimal("100.99"));

        //When
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();

        //Then
        assertEquals(initialNumberOfUsers + 2, numberOfUsers);
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));

        //CleanUp
        userRepository.deleteById(user1.getUserId());
        userRepository.deleteById(user2.getUserId());
    }

    @Test
    public void testFindById() {
        //Given
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        userRepository.save(user);

        //When
        User foundByIdUser = userRepository.findById(user.getUserId()).orElseThrow(UserNotFoundException::new);

        //Then
        assertEquals(user, foundByIdUser);

        //CleanUp
        userRepository.deleteById(user.getUserId());
    }

    @Test
    public void testDelete() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
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
    public void testDeleteById() {
        //Given
        int initialNumberOfUsers = userRepository.findAll().size();
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("email1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
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