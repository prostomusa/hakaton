package com.application.hakaton.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BranchListResponse {

    List<BranchResponse> branchs;
}
