package com.application.hakaton.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity(name = "atm")
@Getter
@Setter
public class Atm extends PersistentEntity {

    private Double latitude;

    private Double longitude;

    private String address;

    private boolean isAllDay;

    @Column(name = "from_time")
    private LocalTime from;

    @Column(name = "to_time")
    private LocalTime to;

    private int currentLoad;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "atm")
    List<AtmServiceEntity> atmServiceEntities;
}
