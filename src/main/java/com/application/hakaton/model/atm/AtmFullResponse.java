package com.application.hakaton.model.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtmFullResponse extends AtmAbstractResponse {

    private List<AtmServicesResponse> services;
}
