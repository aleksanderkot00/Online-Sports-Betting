package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.CategoryDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.CategoryMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/caterogies")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public Set<CategoryDto> getCategories() {
        return categoryMapper.mapToCategoryDtoSet(categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable long categoryId) {
        return categoryMapper.mapToCategoryDto(categoryService.getCategory(categoryId));
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody String name) {
        Category category = new Category();
        category.setName(name);
        return categoryMapper.mapToCategoryDto(categoryService.addCategory(category));
    }

    @PutMapping("/{categoryId}")
    public CategoryDto editCategory(@PathVariable long categoryId, @RequestBody String name) {
        return categoryMapper.mapToCategoryDto(categoryService.changeName(categoryId, name));
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
