package com.application.hakaton.model.atm;

import com.application.hakaton.model.enums.AtmServiceActivityEnum;
import com.application.hakaton.model.enums.AtmServiceEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtmServicesResponse {

    private AtmServiceEnum service;

    private AtmServiceActivityEnum serviceCapability;

    private AtmServiceActivityEnum serviceActivity;

}
