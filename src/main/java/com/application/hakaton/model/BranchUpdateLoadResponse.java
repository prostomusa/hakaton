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
public class BranchUpdateLoadResponse {

    private Long id;

    private List<BranchCertainClient> clients;
}
