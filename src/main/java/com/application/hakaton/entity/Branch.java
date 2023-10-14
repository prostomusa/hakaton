package com.application.hakaton.entity;

import java.util.List;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Type;

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

    @Type(StringArrayType.class)
    @Column(
            name = "services",
            columnDefinition = "text[]"
    )
    private String[] services;

    private boolean hasRamp;

    private String metroStation;

}
