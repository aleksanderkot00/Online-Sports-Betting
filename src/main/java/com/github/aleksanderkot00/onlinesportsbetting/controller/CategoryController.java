package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.CategoryDto;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.NameDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.CategoryMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryMapper.mapToCategoryDtoSet(categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable long categoryId) {
        return categoryMapper.mapToCategoryDto(categoryService.getCategory(categoryId));
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NameDto name) {
        Category category = new Category();
        category.setName(name.getName());
        return categoryMapper.mapToCategoryDto(categoryService.addCategory(category));
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto editCategory(@PathVariable long categoryId, @RequestBody NameDto name) {
        return categoryMapper.mapToCategoryDto(categoryService.changeName(categoryId, name.getName()));
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PatchMapping("/{categoryId}/events/{eventId}")
    public CategoryDto addEventToCategory(@PathVariable long categoryId, @PathVariable long eventId) {
        return categoryMapper.mapToCategoryDto(categoryService.addEvent(categoryId, eventId));
    }
}
