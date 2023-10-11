package com.application.hakaton.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponse {

    private long id;

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;

    private String address;

    private List<ClientTypeEnum> clientTypes;

    private List<OperatingModeResponse> operatingModeResponse;
}
