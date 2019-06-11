package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.EventMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public List<EventDto> getEvents() {
        return eventMapper.mapToEventDtoList(eventService.getEvents());
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable long eventId) {
        return eventMapper.mapToEventDto(eventService.getEvent(eventId));
    }

    @PostMapping
    public EventDto addEvent(@RequestBody EventDto eventDto) {
        return eventMapper.mapToEventDto(eventService.addEvent(eventMapper.mapToEvent(eventDto)));
    }

    @PatchMapping("/{eventId}")
    public EventDto editEvent(@PathVariable long eventId, @RequestBody EventDto eventDto) {
        return eventMapper.mapToEventDto(eventService.editEvent(eventId, eventDto));
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable long eventId) {
        eventService.deleteEvent(eventId);
    }
}
