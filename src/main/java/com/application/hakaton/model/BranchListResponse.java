package com.application.hakaton.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BranchListResponse {

    List<BranchResponse> branchs;
}
