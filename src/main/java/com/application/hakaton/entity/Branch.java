package com.application.hakaton.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "branch")
@Getter
public class Branch extends PersistentEntity {

    Double latitude;

    Double longitude;

    String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch")
    List<ClientTypeBranch> clientTypeBranches;

}
