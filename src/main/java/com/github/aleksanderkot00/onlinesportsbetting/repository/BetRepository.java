package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Bet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BetRepository extends CrudRepository<Bet, Long> {
    @Override
    Bet save(Bet bet);

    @Override
    List<Bet> findAll();

    @Override
    Optional<Bet> findById(Long id);
}
