package com.application.hakaton.entity;

import java.util.List;

import com.application.hakaton.model.enums.ClientTypeEnum;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Type;

@Entity
@Getter
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

    private int currentLoad;

    private int activeWindows;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientTypeBranch")
    private List<BranchOperatingMode> branchOperatingModes;

}
