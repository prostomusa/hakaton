package com.application.hakaton.model.atm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtmAbstractResponse {

    private long id;

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;

    private String address;
}
