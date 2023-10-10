package com.application.hakaton.entity;

import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class PersistentEntity {

    public static final String ID_COLUMN_NAME = "id";

    @Id
    @GeneratedValue
    private Long id;

}
