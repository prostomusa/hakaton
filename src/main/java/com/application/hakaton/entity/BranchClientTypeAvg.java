package com.application.hakaton.entity;

import com.application.hakaton.model.enums.ClientTypeEnum;
import com.application.hakaton.model.enums.DayEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BranchClientTypeAvg extends PersistentEntity {

    @ManyToOne
    private Branch branch;

    @Enumerated(value = EnumType.STRING)
    private ClientTypeEnum clientType;

    @Enumerated(value = EnumType.STRING)
    private DayEnum dayOfWeek;

    private int hour;

    private int avgClients;

    private int avgCustomerServiceTime;
}
