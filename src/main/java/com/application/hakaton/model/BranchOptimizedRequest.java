package com.application.hakaton.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BranchOptimizedRequest {

    List<BranchWithRoadTime> branchs;

}
