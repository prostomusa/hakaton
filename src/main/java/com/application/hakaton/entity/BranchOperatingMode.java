package com.application.hakaton.entity;

import com.application.hakaton.model.enums.DayEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Getter
public class BranchOperatingMode extends PersistentEntity {

    @ManyToOne
    private ClientTypeBranch clientTypeBranch;

    @Enumerated(value = EnumType.STRING)
    private DayEnum workingDay;

    @Column(name = "from_time")
    private LocalTime from;

    @Column(name = "to_time")
    private LocalTime to;

}
