package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.repository.CategoryRepository;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTestSuite {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventRepository eventRepository;

    @Before
    public void init() {
        when(categoryRepository.save(any(Category.class))).then(returnsFirstArg());
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
        Set<Category> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category1));
    }

    @Test
    public void testGetCategories() {
        //When
        Set<Category> categories = categoryService.getCategories();

        //Then
        assertEquals(2, categories.size());
    }

    @Test
    public void testGetCategory() {
        //When
        Category category = categoryService.getCategory(3l);

        //Then
        assertEquals("Test cat 1", category.getName());
        assertEquals(1, category.getEvents().size());
    }

    @Test
    public void testAddCategory() {
        //Given
        Category category = new Category();
        category.setName("NAME");

        //When
        Category retrievedCategory = categoryService.addCategory(category);

        //Then
        assertEquals("NAME", category.getName());
        assertEquals(0, category.getEvents().size());
    }

    @Test
    public void testChangeName() {
        //When
        Category category = categoryService.changeName(313l, "New name");

        //Then
        assertEquals("New name", category.getName());
        assertEquals(1, category.getEvents().size());
    }

    @Test
    public void testDeleteBet() {
        //When
        categoryService.deleteCategory(14l);

        //Then
        verify(categoryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testAddEvent() {
        //Given
        Event event = new Event();
        event.setTeamOneName("Manchester City");
        event.setTeamTwoName("Liverpool");
        event.setDateTime(LocalDateTime.of(2019,5,22,17,45,0));

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        //When
        Category category = categoryService.addEvent(14l, event.getEventId());

        //Then
        assertEquals(2, category.getEvents().size());
    }
}