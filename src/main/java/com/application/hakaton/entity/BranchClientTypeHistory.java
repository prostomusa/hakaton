package com.application.hakaton.entity;

import com.application.hakaton.model.enums.ClientTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
public class BranchClientTypeHistory extends PersistentEntity {

    @ManyToOne
    private Branch branch;

    @Enumerated(value = EnumType.STRING)
    private ClientTypeEnum clientType;

    private LocalDateTime localDateTime;

    private int avgClients;

    private int avgCustomerServiceTime;

}
