package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.details.CategoryWatchingDetails;
import org.springframework.data.repository.CrudRepository;

public interface CategoryWatchingDetailsRepository extends CrudRepository<CategoryWatchingDetails, Long> {
    @Override
    CategoryWatchingDetails save(CategoryWatchingDetails categoryWatchingDetails);
}
