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
    public EventRepository eventRepository;

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event editEvent(long eventId, Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
