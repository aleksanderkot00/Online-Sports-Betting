package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.exception.CategoryNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.exception.EventNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.CategoryRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category changeName(long categoryId, String name) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        category.setName(name);
        return categoryRepository.save(category);
    }

    public void deleteCategory(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category addEvent(long categoryId, long eventId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
        event.setCategory(category);
        category.getEvents().add(event);
        return categoryRepository.save(category);
    }
}
