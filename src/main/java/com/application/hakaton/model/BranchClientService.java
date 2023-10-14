package com.application.hakaton.model;

import com.application.hakaton.model.enums.BranchServiceEnum;
import com.application.hakaton.model.enums.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BranchClientService {

    private ClientTypeEnum clientType;

    private List<BranchServiceEnum> branchServices;
}
