package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Override
    Category save(Category category);

    @Override
    Optional<Category> findById(Long id);

    @Override
    Set<Category> findAll();
}
