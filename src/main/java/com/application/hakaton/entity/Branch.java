package com.application.hakaton.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Branch extends PersistentEntity {

    String name;

    Double latitude;

    Double longitude;

    String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch")
    List<ClientTypeBranch> clientTypeBranches;

    private boolean hasRamp;

    private boolean hasPrime;

    private boolean hasPrivilege;

    private boolean hasChildZone;

    private boolean hasWifi;

    private boolean hasTicketOffice;

    private boolean allowPets;

    private String metroStation;

}
