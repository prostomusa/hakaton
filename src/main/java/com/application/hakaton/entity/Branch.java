package com.application.hakaton.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Branch extends PersistentEntity {

    Double latitude;

    Double longitude;

    Integer currentLoad;
}
