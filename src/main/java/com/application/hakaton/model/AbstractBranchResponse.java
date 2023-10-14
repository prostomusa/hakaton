package com.application.hakaton.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AbstractBranchResponse {

    private long id;

    @JsonProperty("long")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;

    private String address;
}
