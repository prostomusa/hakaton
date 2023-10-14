package com.application.hakaton.model;

import com.application.hakaton.model.enums.BranchServiceEnum;
import com.application.hakaton.model.enums.ClientTypeEnum;
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
public class BranchRequest {

    private CoordRequest firstCorner;

    private CoordRequest lastCorner;

    private ClientTypeEnum clientType = ClientTypeEnum.PHYSICAL;

    private List<BranchServiceEnum> branchServices;

    private boolean hasRamp;

    private String metroStation;

    private String address;

    @JsonProperty("holidayWorking")
    private boolean holidayWorking;

    @JsonProperty("hasPrime")
    private boolean hasPrime;

    @JsonProperty("hasPrivilege")
    private boolean hasPrivilege;

    @JsonProperty("hasChildZone")
    private boolean hasChildZone;

    @JsonProperty("hasWifi")
    private boolean hasWifi;

    @JsonProperty("hasTicketOffice")
    private boolean hasTicketOffice;

    @JsonProperty("allowPets")
    private boolean allowPets;

    @JsonProperty("workingNow")
    private boolean workingNow;

}
