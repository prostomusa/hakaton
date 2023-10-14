package com.application.hakaton.entity;

import java.util.List;

import com.application.hakaton.model.enums.ClientTypeEnum;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
public class ClientTypeBranch extends PersistentEntity {

    @Enumerated(value = EnumType.STRING)
    private ClientTypeEnum clientType;

    @ManyToOne
    private Branch branch;

    @Type(StringArrayType.class)
    @Column(
            name = "holidays",
            columnDefinition = "text[]"
    )
    private String[] holidays;

    @Type(StringArrayType.class)
    @Column(
            name = "services",
            columnDefinition = "text[]"
    )
    private String[] services;

    private int currentLoad;

    private int activeWindows;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientTypeBranch")
    private List<BranchOperatingMode> branchOperatingModes;

}
