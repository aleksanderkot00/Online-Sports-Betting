package com.github.aleksanderkot00.onlinesportsbetting.repository;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.exception.CategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTestSuite {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
       int initialNumberOfCategories = categoryRepository.findAll().size();
        Event event1 = new Event();
        event1.setTeamOneName("Real Madryt");
        event1.setTeamTwoName("Barcelona");
        event1.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event1.setTeamOneScore(BigDecimal.ONE);
        event1.setTeamTwoScore(BigDecimal.ZERO);

        Event event2 = new Event();
        event2.setTeamOneName("Manchester City");
        event2.setTeamTwoName("Liverpool");
        event2.setDateTime(LocalDateTime.of(2019,5,22,17,45,0));

        Category category1 = new Category();
        category1.setName("Test cat 1");
        category1.getEvents().add(event1);

        Category category2 = new Category();
        category2.setName("Test  cat 1");
        category2.getEvents().add(event2);
        event1.setCategory(category1);
        event2.setCategory(category2);

        //When
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        List<Category> categories = categoryRepository.findAll();

        //Then
        assertEquals(initialNumberOfCategories + 2, categories.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        Category category = new Category();
        category.setName("Test cat 1");
        categoryRepository.save(category);

        //When
        Category retrievedCategory = categoryRepository.findById(category.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        //Then
        assertEquals(category, retrievedCategory);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        Category category = new Category();
        category.setName("Test cat 1");
        categoryRepository.save(category);

        //When
        categoryRepository.deleteById(category.getCategoryId());
        List<Category> categories = categoryRepository.findAll();

        //Then
        assertFalse(categories.contains(category));
    }
}