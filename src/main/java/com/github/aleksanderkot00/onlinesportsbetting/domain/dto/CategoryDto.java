package com.github.aleksanderkot00.onlinesportsbetting.domain.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class CategoryDto {
    private long categoryId;
    private String name;
    private Set<EventDto> events;

    public CategoryDto(long categoryId, String name, Set<EventDto> events) {
        this.categoryId = categoryId;
        this.name = name;
        this.events = events;
    }
}
