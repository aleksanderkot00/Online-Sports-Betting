package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (userDto.getName() != "" && userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getLastName() != "" && userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if (userDto.getEmail() != "" && userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != "" && userDto.getPassword() != null) user.setEncryptedPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.getRoles().clear();
        userRepository.save(user);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found!"));
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
         return new org.springframework.security.core.userdetails.User(
                 user.getEmail(), user.getEncryptedPassword(), user.isActive(),
                 true, true, true, authorities
         );
    }
}
