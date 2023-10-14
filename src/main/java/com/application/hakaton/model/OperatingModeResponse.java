package com.application.hakaton.model;

import com.application.hakaton.model.enums.ClientTypeEnum;
import com.application.hakaton.model.enums.DayEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OperatingModeResponse {

    private ClientTypeEnum type;

    private List<DayEnum> holidays;

    private List<WorkingDateTime> workingDateTime;

    private int currentLoad;

    public OperatingModeResponse(
            ClientTypeEnum type,
            String[] holidays,
            List<WorkingDateTime> workingDateTime,
            int currentLoad
    ) {
        this.type = type;
        this.holidays = Arrays.stream(holidays).map(DayEnum::valueOf).collect(Collectors.toList());
        this.workingDateTime = workingDateTime;
        this.currentLoad = currentLoad;
    }
}
