package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        event.setFinished(Optional.of(eventDto.isFinished()).orElse(false));

        return event;
    }

    public EventDto mapToEventDto(Event event) {
        String categoryName = null;
        if (event.getCategory() != null) {
            categoryName = event.getCategory().getName();
        }
        return EventDto.builder()
                .categoryName(categoryName)
                .dateTime(event.getDateTime())
                .eventId(event.getEventId())
                .teamOneName(event.getTeamOneName())
                .teamTwoName(event.getTeamTwoName())
                .teamOneScore(event.getTeamOneScore())
                .teamTwoScore(event.getTeamTwoScore())
                .finished(event.isFinished())
                .build();
    }

    public List<EventDto> mapToEventDtoList(List<Event> events) {
        return events.stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
