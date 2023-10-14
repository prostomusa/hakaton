package com.application.hakaton.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordRequest {

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;
}
