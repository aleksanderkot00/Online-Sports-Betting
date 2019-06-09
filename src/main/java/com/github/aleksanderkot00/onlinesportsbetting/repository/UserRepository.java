package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    User save(User user);

    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
