package com.github.aleksanderkot00.onlinesportsbetting.mapper;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event mapToEvent(EventDto eventDto) {
        Event event = new Event();
        event.setDateTime(eventDto.getDateTime());
        event.setTeamOneName(eventDto.getTeamOneName());
        event.setTeamTwoName(eventDto.getTeamTwoName());
        event.setResult(eventDto.getResult());

        return event;
    }
}
