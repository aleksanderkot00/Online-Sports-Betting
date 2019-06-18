package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Category;
import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTestSuite {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Before
    public void init() {
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
        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event1));
        when(eventRepository.save(any(Event.class))).then(returnsFirstArg());
    }

    @Test
    public void testGetEvents() {
        //When
        List<Event> events = eventService.getEvents();

        //Then
        assertEquals(2, events.size());
        assertEquals("Real Madryt", events.get(0).getTeamOneName());
        assertEquals("Liverpool", events.get(1).getTeamTwoName());

    }

    @Test
    public void testGetEvent() {
        //When
        Event event = eventService.getEvent(31);

        //Then
        assertEquals("Real Madryt", event.getTeamOneName());
        assertEquals("Barcelona", event.getTeamTwoName());
        assertEquals(BigDecimal.ZERO, event.getTeamTwoScore());
    }

    @Test
    public void testAddEvent() {
        //Given
        Event event = new Event();
        event.setTeamOneName("legia");
        event.setTeamTwoName("lech");
        event.setDateTime(LocalDateTime.of(2019,5,22,17,45,0));
        event.setTeamTwoScore(BigDecimal.ONE);
        event.setTeamOneScore(BigDecimal.ONE);

        //When
        Event retrievedEvent = eventService.addEvent(event);

        //Then
        assertEquals(BigDecimal.ONE, retrievedEvent.getTeamOneScore());
        assertEquals(BigDecimal.ONE, retrievedEvent.getTeamTwoScore());
        assertEquals("legia", retrievedEvent.getTeamOneName());
        assertEquals("lech", retrievedEvent.getTeamTwoName());
        assertEquals(LocalDateTime.of(2019,5,22,17,45,0), event.getDateTime());
    }

    @Test
    public void testEditEvent() {
        //Given
        EventDto eventDto = EventDto.builder()
                .teamOneName("")
                .teamTwoName("Bayern")
                .teamOneScore(BigDecimal.TEN)
                .build();

        //When
        Event retrievedEvent = eventService.editEvent(12l, eventDto);

        //Then
//        assertEquals(BigDecimal.TEN, retrievedEvent.getTeamOneScore());
        assertEquals(BigDecimal.ZERO, retrievedEvent.getTeamTwoScore());
        assertEquals("Real Madryt", retrievedEvent.getTeamOneName());
        assertEquals("Bayern", retrievedEvent.getTeamTwoName());
        assertEquals(LocalDateTime.of(2019,12,12,20,30,0),retrievedEvent.getDateTime());
    }

    @Test
    public void testDeleteBet() {
        //When
        eventService.deleteEvent(14l);

        //Then
        verify(eventRepository, times(1)).deleteById(anyLong());
    }
}