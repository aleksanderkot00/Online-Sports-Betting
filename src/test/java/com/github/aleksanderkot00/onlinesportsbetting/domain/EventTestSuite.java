package com.github.aleksanderkot00.onlinesportsbetting.domain;

import com.github.aleksanderkot00.onlinesportsbetting.exception.EventNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventTestSuite {

    private final EventRepository eventRepository;

    @Autowired
    public EventTestSuite(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testSaveAndFindAll() {
        //Given
        int initialNumberOfEvents = eventRepository.findAll().size();
        Event event1 = new Event();
        event1.setTeamOneName("Real Madryt");
        event1.setTeamTwoName("Barcelona");
        event1.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event1.setResult(Result.ONE);

        Event event2 = new Event();
        event2.setTeamOneName("Manchester City");
        event2.setTeamTwoName("Liverpool");
        event2.setDateTime(LocalDateTime.of(2019,5,22,17,45,0));
        event2.setResult(Result.TWO);

        //When
        eventRepository.save(event1);
        eventRepository.save(event2);
        List<Event> events = eventRepository.findAll();
        int numberOfEvents = events.size();

        //Then
        assertEquals(initialNumberOfEvents + 2, numberOfEvents);
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event2));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testFindById() {
        //Given
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event.setResult(Result.ONE);
        eventRepository.save(event);

        //When
        Event foundEvent = eventRepository.findById(event.getEventId()).orElseThrow(EventNotFoundException::new);

        //Then
        assertEquals(event, foundEvent);
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDelete() {
        //Given
        int initialNumberOfEvents = eventRepository.findAll().size();
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event.setResult(Result.ONE);
        eventRepository.save(event);

        //When
        eventRepository.delete(event);
        List<Event> events = eventRepository.findAll();
        int numberOfEvents = events.size();

        //Then
        assertEquals(initialNumberOfEvents, numberOfEvents);
        assertFalse(events.contains(event));
    }

    @Test
    @Transactional(rollbackOn = {Exception.class})
    public void testDeleteById() {
        //Given
        int initialNumberOfEvents = eventRepository.findAll().size();
        Event event = new Event();
        event.setTeamOneName("Real Madryt");
        event.setTeamTwoName("Barcelona");
        event.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event.setResult(Result.ONE);
        eventRepository.save(event);

        //When
        eventRepository.deleteById(event.getEventId());
        List<Event> events = eventRepository.findAll();
        int numberOfEvents = events.size();

        //Then
        assertEquals(initialNumberOfEvents, numberOfEvents);
        assertFalse(events.contains(event));
    }
}