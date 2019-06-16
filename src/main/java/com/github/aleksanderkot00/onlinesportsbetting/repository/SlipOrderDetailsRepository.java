package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.details.SlipOrderDetails;
import org.springframework.data.repository.CrudRepository;

public interface SlipOrderDetailsRepository extends CrudRepository<SlipOrderDetails, Long> {
    @Override
    SlipOrderDetails save(SlipOrderDetails SlipOrderDetails);
}
