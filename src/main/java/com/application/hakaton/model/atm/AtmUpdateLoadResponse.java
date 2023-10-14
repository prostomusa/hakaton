package com.application.hakaton.model.atm;

import com.application.hakaton.model.BranchCertainClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtmUpdateLoadResponse {

    private Long id;

    private int currentLoad;
}
