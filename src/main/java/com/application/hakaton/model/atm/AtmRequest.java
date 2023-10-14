package com.application.hakaton.model.atm;

import com.application.hakaton.model.CoordRequest;
import com.application.hakaton.model.enums.AtmServiceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AtmRequest {

    private CoordRequest firstCorner;

    private CoordRequest lastCorner;

    private List<AtmServiceEnum> services;

    private String address;

    @JsonProperty("allDay")
    private boolean allDay;

    @JsonProperty("workingNow")
    private boolean workingNow;

}
