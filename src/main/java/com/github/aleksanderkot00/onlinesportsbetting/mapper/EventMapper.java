package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    public Event mapToEvent(EventDto eventDto) {
        Event event = new Event();
        event.setDateTime(eventDto.getDateTime());
        event.setTeamOneName(eventDto.getTeamOneName());
        event.setTeamTwoName(eventDto.getTeamTwoName());
        event.setTeamOneScore(eventDto.getTeamOneScore());
        event.setTeamTwoScore(eventDto.getTeamTwoScore());

        return event;
    }

    public EventDto mapToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setEventId(event.getEventId());
        eventDto.setCategoryName(event.getCategory().getName());
        eventDto.setDateTime(event.getDateTime());
        eventDto.setTeamOneName(event.getTeamOneName());
        eventDto.setTeamTwoName(event.getTeamTwoName());
        eventDto.setTeamOneScore(event.getTeamOneScore());
        eventDto.setTeamTwoScore(event.getTeamTwoScore());

        return eventDto;
    }

    public List<EventDto> mapToEventDtoList(List<Event> events) {
        return events.stream()
                .map(event -> mapToEventDto(event))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
