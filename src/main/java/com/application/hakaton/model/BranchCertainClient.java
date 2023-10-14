package com.application.hakaton.model;

import com.application.hakaton.model.enums.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BranchCertainClient {

    private ClientTypeEnum clientType;

    private Integer currentLoad;

}
