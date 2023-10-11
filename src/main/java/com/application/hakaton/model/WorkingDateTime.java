package com.application.hakaton.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.ZoneOffset;

@Getter
@Setter
public class WorkingDateTime {

    DayEnum day;

    LocalTime from;

    LocalTime to;

    public WorkingDateTime(DayEnum day, LocalTime from, LocalTime to) {
        this.day = day;
        this.from = from.atOffset(ZoneOffset.of("+3")).toLocalTime();
        this.to = to.atOffset(ZoneOffset.of("+3")).toLocalTime();
    }
}
