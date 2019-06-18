package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.UserRegistrationDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.UserNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.RoleRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User addUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setName(userRegistrationDto.getName());
                user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setEncryptedPassword(encoder.encode(userRegistrationDto.getPassword()));
        user.getRoles().add(roleRepository.findByRole("USER").orElse(new Role()));

        return userRepository.save(user);
    }

    public User editUser(long userId, UserRegistrationDto userRegistrationDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (userRegistrationDto.getName() != null && !userRegistrationDto.getName().equals("")) {
            user.setName(userRegistrationDto.getName());
        }
        if (userRegistrationDto.getLastName() != null && !userRegistrationDto.getLastName().equals("")) {
            user.setLastName(userRegistrationDto.getLastName());
        }
        if (userRegistrationDto.getEmail() != null && !userRegistrationDto.getEmail().equals("")) {
            user.setEmail(userRegistrationDto.getEmail());
        }
        if (userRegistrationDto.getPassword() != null && !userRegistrationDto.getPassword().equals("")) {
            user.setEncryptedPassword(encoder.encode(userRegistrationDto.getPassword()));
        }
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
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toSet());
         return new org.springframework.security.core.userdetails.User(
                 user.getEmail(), user.getEncryptedPassword(), user.isActive(),
                 true, true, true, authorities);
    }
}
