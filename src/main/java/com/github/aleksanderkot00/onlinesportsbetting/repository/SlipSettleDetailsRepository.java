package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.details.SlipSettleDetails;
import org.springframework.data.repository.CrudRepository;

public interface SlipSettleDetailsRepository extends CrudRepository<SlipSettleDetails, Long> {
    @Override
    SlipSettleDetails save(SlipSettleDetails SlipSettleDetails);
}
