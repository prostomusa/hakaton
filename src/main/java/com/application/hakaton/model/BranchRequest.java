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
public class BranchRequest {

    private CoordRequest firstCorner;

    private CoordRequest lastCorner;

    private List<ClientTypeEnum> clientTypeEnums;

    private boolean hasRamp;

    private String metroStation;

    private String address;

    private boolean holidayWorking;

}
