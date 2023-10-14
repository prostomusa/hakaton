package com.application.hakaton.model;

import com.application.hakaton.model.enums.DayEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.ZoneOffset;

@Getter
@Setter
public class WorkingDateTime {

    DayEnum day;

    @JsonFormat(pattern = "HH:mm")
    LocalTime from;

    @JsonFormat(pattern = "HH:mm")
    LocalTime to;

    public WorkingDateTime(DayEnum day, LocalTime from, LocalTime to) {
        this.day = day;
        this.from = from.atOffset(ZoneOffset.of("+3")).toLocalTime();
        this.to = to.atOffset(ZoneOffset.of("+3")).toLocalTime();
    }
}
