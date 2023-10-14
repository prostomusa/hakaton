package com.application.hakaton.entity;

import com.application.hakaton.model.enums.AtmServiceActivityEnum;
import com.application.hakaton.model.enums.AtmServiceEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity(name = "atm_service")
@Getter
public class AtmServiceEntity extends PersistentEntity {

    @ManyToOne
    private Atm atm;

    @Enumerated(value = EnumType.STRING)
    private AtmServiceEnum service;

    @Enumerated(value = EnumType.STRING)
    private AtmServiceActivityEnum serviceCapability;

    @Enumerated(value = EnumType.STRING)
    private AtmServiceActivityEnum serviceActivity;

}
