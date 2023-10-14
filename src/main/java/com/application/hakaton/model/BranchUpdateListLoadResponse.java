package com.application.hakaton.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchUpdateListLoadResponse {
    private List<BranchUpdateLoadResponse> branchUpdateLoads;
}
