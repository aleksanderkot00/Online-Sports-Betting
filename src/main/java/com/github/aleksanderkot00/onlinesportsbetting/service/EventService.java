package com.github.aleksanderkot00.onlinesportsbetting.service;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import com.github.aleksanderkot00.onlinesportsbetting.exception.EventNotFoundException;
import com.github.aleksanderkot00.onlinesportsbetting.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
    }

    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event editEvent(long eventId, EventDto eventDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);
        if (eventDto.getDateTime() != null) {
            event.setDateTime(eventDto.getDateTime());
        }
        if (!eventDto.getTeamOneName().equals("") && eventDto.getTeamOneName() != null) {
            event.setTeamOneName(eventDto.getTeamOneName());
        }
        if (!eventDto.getTeamTwoName().equals("") && eventDto.getTeamTwoName() != null) {
            event.setTeamTwoName(eventDto.getTeamTwoName());
        }
        if (eventDto.getTeamOneScore() != null){
            event.setTeamOneScore(eventDto.getTeamOneScore());
        }
        if (eventDto.getTeamTwoScore() != null) {
            event.setTeamTwoScore(eventDto.getTeamTwoScore());
        }

        return eventRepository.save(event);
    }

    public void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
