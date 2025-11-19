package com.techieplanet.application.model;

import com.techieplanet.application.libs.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "students")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public Student(String name) {
        this.name = name;
    }
}