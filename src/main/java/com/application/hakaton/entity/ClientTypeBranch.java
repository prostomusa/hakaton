package com.application.hakaton.entity;

import com.application.hakaton.model.ClientTypeEnum;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.util.List;

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
    @Enumerated(value = EnumType.STRING)
    private String[] holidays;

    private int currentLoad;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientTypeBranch")
    private List<OperatingMode> operatingModes;

}
