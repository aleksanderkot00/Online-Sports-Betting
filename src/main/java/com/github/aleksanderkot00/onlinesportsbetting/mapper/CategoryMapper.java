package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.CategoryDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final EventMapper eventMapper;

    @Autowired
    public CategoryMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public CategoryDto mapToCategoryDto(Category category) {
        Set<EventDto> events = category.getEvents().stream()
                .map(eventMapper::mapToEventDto)
                .collect(Collectors.toSet());
        return new CategoryDto(category.getCategoryId(), category.getName(), events);
    }

    public Set<CategoryDto> mapToCategoryDtoSet(Set<Category> categories) {
        return categories.stream()
                .map(this::mapToCategoryDto)
                .collect(Collectors.toSet());
    }
}
