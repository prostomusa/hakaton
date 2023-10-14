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

    private List<String> branchServices;

    private List<ClientTypeEnum> clientTypes;

    private List<OperatingModeResponse> operatingModeResponse;
}
