package com.application.hakaton.model;

import com.application.hakaton.model.enums.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchFullResponse extends AbstractBranchResponse {

    private String metroStation;

    private Boolean hasRamp;

    private String name;

    private List<BranchClientService> branchClientServices;

    private List<OperatingModeResponse> operatingModeResponse;

    private boolean hasPrime;

    private boolean hasPrivilege;

    private boolean hasChildZone;

    private boolean hasWifi;

    private boolean hasTicketOffice;

    private boolean allowPets;
}
