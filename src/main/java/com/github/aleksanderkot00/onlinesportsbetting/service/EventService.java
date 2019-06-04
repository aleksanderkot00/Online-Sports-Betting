package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.exception.EventNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    private List<Event> getEvents() {
        return eventRepository.findAll();
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    private Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    private Event editEvent(long eventId, Event event) {
        return eventRepository.save(event);
    }

    private void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
