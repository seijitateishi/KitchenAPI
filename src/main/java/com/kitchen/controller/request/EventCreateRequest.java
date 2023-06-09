package com.kitchen.controller.request;

import com.kitchen.enums.TypeOfEvent;
import com.kitchen.model.Event;
import com.kitchen.service.EventService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String description;

    @NotNull
    private LocalDateTime listLimitTime;

    @NotNull
    private LocalDateTime eventTime;

    @NotNull
    private TypeOfEvent type;

    public void save(EventService eventService) {
        if (listLimitTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("List limit time must be before event time");
        }
        if (eventTime.isBefore(listLimitTime)) {
            throw new IllegalArgumentException("Event time must be after List limit time");
        }
        eventService.save(Event.builder()
                .description(description)
                .listLimitTime(listLimitTime)
                .eventTime(eventTime)
                .typeOfEvent(type)
                .build());
    }

}
