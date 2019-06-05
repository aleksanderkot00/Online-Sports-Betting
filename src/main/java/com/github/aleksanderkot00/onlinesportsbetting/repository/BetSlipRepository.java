package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.BetSlip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BetSlipRepository extends CrudRepository<BetSlip, Long> {
    @Override
    BetSlip save(BetSlip betSlip);

    @Override
    List<BetSlip> findAll();

    @Override
    Optional<BetSlip> findById(Long id);
}
