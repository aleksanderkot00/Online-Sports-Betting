package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    @Override
    Event save(Event event);

    @Override
    List<Event> findAll();

    @Override
    Optional<Event> findById(Long id);
}
