package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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
        user1.setEmail("emaiqql1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user1.getRoles().add(role);

        User user2 = new User();
        user2.setName("Test Name2");
        user2.setLastName("Test Lastname2");
        user2.setEmail("email211@test.com");
        user2.setBalance(new BigDecimal("100.99"));
        user2.setEncryptedPassword("Password123");
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
        user.setEmail("email111@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
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
        user.setEmail("email1sa@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
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
        user.setEmail("email11x1@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
        userRepository.save(user);

        //When
        userRepository.deleteById(user.getUserId());
        List<User> users = userRepository.findAll();
        int numberOfUsers = users.size();

        //Then
        assertEquals(initialNumberOfUsers, numberOfUsers);
        assertFalse(users.contains(user));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindByCartSlip() {
        //Given
        User user = new User();
        user.setName("Test Name1");
        user.setLastName("Test Lastname1");
        user.setEmail("emaiasa@test.com");
        user.setBalance(new BigDecimal("1231.11"));
        user.setEncryptedPassword("Password123");
        Slip cartSlip = new Slip();
        cartSlip.setStake(BigDecimal.valueOf(123.12));
        user.setCartSlip(cartSlip);
        userRepository.save(user);

        //When
        User retrievedUser = userRepository.findByCartSlip(cartSlip).orElseThrow(UserNotFoundException::new);

        //Then
        assertEquals(retrievedUser.getCartSlip().getStake(), BigDecimal.valueOf(123.12));
        assertEquals(retrievedUser.getName(), "Test Name1");

    }
}