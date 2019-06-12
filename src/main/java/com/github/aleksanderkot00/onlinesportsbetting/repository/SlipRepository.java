package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Slip;
import com.github.aleksanderkot00.onlinesportsbetting.domain.SlipState;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SlipRepository extends CrudRepository<Slip, Long> {
    @Override
    Slip save(Slip slip);

    @Override
    List<Slip> findAll();

    @Override
    Optional<Slip> findById(Long id);

    List<Slip> findAllByState(SlipState state);
}
