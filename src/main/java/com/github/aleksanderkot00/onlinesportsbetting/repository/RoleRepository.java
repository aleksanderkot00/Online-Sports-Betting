package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Override
    Role save(Role role);

    Optional<Role> findByRole(String role);
}
