package com.github.aleksanderkot00.onlinesportsbetting.controller;

import com.github.aleksanderkot00.onlinesportsbetting.domain.Event;
import com.github.aleksanderkot00.onlinesportsbetting.domain.dto.EventDto;
import com.github.aleksanderkot00.onlinesportsbetting.mapper.EventMapper;
import com.github.aleksanderkot00.onlinesportsbetting.service.EventService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class, secure = false)
public class EventControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventMapper eventMapper;

    @Test
    public void testGetEvents() throws Exception {
        //Given
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
        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        EventDto eventDto1 = EventDto.builder()
                .eventId(event1.getEventId())
                .categoryName("Liga hiszpanska")
                .dateTime(event1.getDateTime())
                .teamOneName(event1.getTeamOneName())
                .teamTwoName(event1.getTeamTwoName())
                .teamOneScore(event1.getTeamOneScore())
                .teamTwoScore(event1.getTeamTwoScore())
                .build();
        EventDto eventDto2 = EventDto.builder()
                .eventId(event2.getEventId())
                .dateTime(event2.getDateTime())
                .teamOneName(event2.getTeamOneName())
                .teamTwoName(event2.getTeamTwoName())
                .build();
        List<EventDto> eventDtos = new ArrayList<>();
        eventDtos.add(eventDto1);
        eventDtos.add(eventDto2);

        when(eventMapper.mapToEventDtoList(events)).thenReturn(eventDtos);
        when(eventService.getEvents()).thenReturn(events);

        //When&Then
        mockMvc.perform(get("/events").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].teamOneName", is("Real Madryt")))
                .andExpect(jsonPath("$[0].teamOneScore", is(1)))
                .andExpect(jsonPath("$[0].categoryName", is("Liga hiszpanska")))
                .andExpect(jsonPath("$[0].teamTwoName", is("Barcelona")))
                .andExpect(jsonPath("$[0].teamTwoScore", is(0)))
                .andExpect(jsonPath("$[1].teamOneName", is("Manchester City")))
                .andExpect(jsonPath("$[1].teamTwoName", is("Liverpool")))
                .andExpect(jsonPath("$[1].dateTime", is("2019-05-22T17:45:00")));
    }

    @Test
    public void testGetEvent() throws Exception {
        //Given
        Event event1 = new Event();
        event1.setTeamOneName("Real Madryt");
        event1.setTeamTwoName("Barcelona");
        event1.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event1.setTeamOneScore(BigDecimal.ONE);
        event1.setTeamTwoScore(BigDecimal.ZERO);

        EventDto eventDto1 = EventDto.builder()
                .eventId(event1.getEventId())
                .categoryName("Liga hiszpanska")
                .dateTime(event1.getDateTime())
                .teamOneName(event1.getTeamOneName())
                .teamTwoName(event1.getTeamTwoName())
                .teamOneScore(event1.getTeamOneScore())
                .teamTwoScore(event1.getTeamTwoScore())
                .build();

        when(eventMapper.mapToEventDto(event1)).thenReturn(eventDto1);
        when(eventService.getEvent(anyLong())).thenReturn(event1);

        //When&Then
        mockMvc.perform(get("/events/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamOneName", is("Real Madryt")))
                .andExpect(jsonPath("$.teamOneScore", is(1)))
                .andExpect(jsonPath("$.categoryName", is("Liga hiszpanska")))
                .andExpect(jsonPath("$.teamTwoName", is("Barcelona")))
                .andExpect(jsonPath("$.teamTwoScore", is(0)))
                .andExpect(jsonPath("$.dateTime", is("2019-12-12T20:30:00")));
    }

    @Test
    public void testAddEvent() throws Exception {
        //Given
        Event event1 = new Event();
        event1.setTeamOneName("Real Madryt");
        event1.setTeamTwoName("Barcelona");
        event1.setDateTime(LocalDateTime.of(2019,12,12,20,30,0));
        event1.setTeamOneScore(BigDecimal.ONE);
        event1.setTeamTwoScore(BigDecimal.ZERO);

        EventDto eventDto1 = EventDto.builder()
                .eventId(event1.getEventId())
                .categoryName("Liga hiszpanska")
                .teamOneName(event1.getTeamOneName())
                .teamTwoName(event1.getTeamTwoName())
                .teamOneScore(event1.getTeamOneScore())
                .teamTwoScore(event1.getTeamTwoScore())
                .build();
        Gson gson = new Gson();
        String jsonContent = gson.toJson(eventDto1);

        when(eventMapper.mapToEvent(any(EventDto.class))).thenReturn(event1);
        when(eventMapper.mapToEventDto(event1)).thenReturn(eventDto1);
        when(eventService.addEvent(any(Event.class))).then(returnsFirstArg());

        //When&Then
        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamOneName", is("Real Madryt")))
                .andExpect(jsonPath("$.teamOneScore", is(1)))
                .andExpect(jsonPath("$.categoryName", is("Liga hiszpanska")))
                .andExpect(jsonPath("$.teamTwoName", is("Barcelona")))
                .andExpect(jsonPath("$.teamTwoScore", is(0)));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        //Given
        Event event1 = new Event();
        event1.setTeamOneName("Real Madryt");
        event1.setTeamTwoName("Barcelona");
        event1.setTeamOneScore(BigDecimal.ONE);
        event1.setTeamTwoScore(BigDecimal.ZERO);

        EventDto eventDto1 = EventDto.builder()
                .eventId(event1.getEventId())
                .categoryName("Liga hiszpanska")
                .teamOneName(event1.getTeamOneName())
                .teamTwoName(event1.getTeamTwoName())
                .teamOneScore(event1.getTeamOneScore())
                .teamTwoScore(event1.getTeamTwoScore())
                .build();
        Gson gson = new Gson();
        String jsonContent = gson.toJson(eventDto1);

        when(eventMapper.mapToEvent(any(EventDto.class))).thenReturn(event1);
        when(eventMapper.mapToEventDto(event1)).thenReturn(eventDto1);
        when(eventService.editEvent(anyLong(),any(EventDto.class))).thenReturn(event1);

        //When&Then
        mockMvc.perform(patch("/events/21").contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamOneName", is("Real Madryt")))
                .andExpect(jsonPath("$.teamOneScore", is(1)))
                .andExpect(jsonPath("$.categoryName", is("Liga hiszpanska")))
                .andExpect(jsonPath("$.teamTwoName", is("Barcelona")))
                .andExpect(jsonPath("$.teamTwoScore", is(0)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //When&Then
        mockMvc.perform(delete("/events/13")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(eventService, times(1)).deleteEvent(13);
    }
}