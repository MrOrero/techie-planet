package com.techieplanet.application.modules.core.model;

import com.techieplanet.application.libs.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "subjects")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Subject extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public Subject(String name) {
        this.name = name;
    }
}