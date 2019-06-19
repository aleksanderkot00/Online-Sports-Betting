package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.UserMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void testGetUsers() throws Exception {
        //Given
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user1.getRoles().add(role);

        User user2 = new User();
        user2.setName("Test Name2");
        user2.setLastName("Test Lastname2");
        user2.setEmail("email2@test.com");
        user2.setBalance(BigDecimal.ZERO);
        user2.setEncryptedPassword("Password123");
        user2.getRoles().add(role);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        UserDto userDto1 = UserDto.builder()
                .userId(user1.getUserId())
                .name(user1.getName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .active(user1.isActive())
                .roles(user1.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()))
                .balance(user1.getBalance())
                .build();

        UserDto userDto2 = UserDto.builder()
                .userId(user2.getUserId())
                .name(user2.getName())
                .lastName(user2.getLastName())
                .email(user2.getEmail())
                .active(user2.isActive())
                .roles(user2.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()))
                .balance(user2.getBalance())
                .build();
        List<UserDto> usersDto = new ArrayList<>();
        usersDto.add(userDto1);
        usersDto.add(userDto2);

        when(userMapper.mapToUserDtoList(users)).thenReturn(usersDto);
        when(userService.getUsers()).thenReturn(users);

        //When&Then
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test Name1")))
                .andExpect(jsonPath("$[0].lastName", is("Test Lastname1")))
                .andExpect(jsonPath("$[0].email", is("email1@test.com")))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[0].balance", is(1231.11)))
                .andExpect(jsonPath("$[0].roles", hasSize(1)))
                .andExpect(jsonPath("$[1].name", is("Test Name2")))
                .andExpect(jsonPath("$[1].lastName", is("Test Lastname2")))
                .andExpect(jsonPath("$[1].email", is("email2@test.com")))
                .andExpect(jsonPath("$[1].active", is(true)))
                .andExpect(jsonPath("$[1].balance", is(0)))
                .andExpect(jsonPath("$[1].roles", hasSize(1)));
    }

    @Test
    public void testGetUser() throws Exception {
        //Given
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user1.getRoles().add(role);

        UserDto userDto1 = UserDto.builder()
                .userId(user1.getUserId())
                .name(user1.getName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .active(user1.isActive())
                .roles(user1.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()))
                .balance(user1.getBalance())
                .build();

        when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);
        when(userService.getUser(14)).thenReturn(user1);

        //When&Then
        mockMvc.perform(get("/users/14").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Name1")))
                .andExpect(jsonPath("$.lastName", is("Test Lastname1")))
                .andExpect(jsonPath("$.email", is("email1@test.com")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.balance", is(1231.11)))
                .andExpect(jsonPath("$.roles", hasSize(1)));
    }

    @Test
    public void addGetUser() throws Exception {
        //Given
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user1.getRoles().add(role);

        UserDto userDto1 = UserDto.builder()
                .userId(user1.getUserId())
                .name(user1.getName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .active(user1.isActive())
                .roles(user1.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()))
                .balance(user1.getBalance())
                .build();
        UserRegistrationDto registrationDto = new UserRegistrationDto(user1.getName(),user1.getLastName(),
                user1.getEmail(), "encPass");
        when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);
        when(userService.addUser(any(UserRegistrationDto.class))).thenReturn(user1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(registrationDto);

        //When&Then
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Name1")))
                .andExpect(jsonPath("$.lastName", is("Test Lastname1")))
                .andExpect(jsonPath("$.email", is("email1@test.com")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.balance", is(1231.11)))
                .andExpect(jsonPath("$.roles", hasSize(1)));
    }

    @Test
    public void editGetUser() throws Exception {
        //Given
        User user1 = new User();
        user1.setName("Test Name1");
        user1.setLastName("Test Lastname1");
        user1.setEmail("email1@test.com");
        user1.setBalance(new BigDecimal("1231.11"));
        user1.setEncryptedPassword("Password23");
        Role role = new Role();
        role.setRole("USER");
        user1.getRoles().add(role);

        UserDto userDto1 = UserDto.builder()
                .userId(user1.getUserId())
                .name(user1.getName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .active(user1.isActive())
                .roles(user1.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()))
                .balance(user1.getBalance())
                .build();
        UserRegistrationDto registrationDto = new UserRegistrationDto(user1.getName(),user1.getLastName(),
                user1.getEmail(), "encPass");
        when(userMapper.mapToUserDto(user1)).thenReturn(userDto1);
        when(userService.editUser(anyLong(), any(UserRegistrationDto.class))).thenReturn(user1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(registrationDto);

        //When&Then
        mockMvc.perform(patch("/users/13").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Name1")))
                .andExpect(jsonPath("$.lastName", is("Test Lastname1")))
                .andExpect(jsonPath("$.email", is("email1@test.com")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.balance", is(1231.11)))
                .andExpect(jsonPath("$.roles", hasSize(1)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //When&Then
        mockMvc.perform(delete("/users/13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(13);
    }
}