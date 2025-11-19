package com.techieplanet.application.modules.core.model;

import com.techieplanet.application.libs.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "scores")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Score extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "score", nullable = false)
    private Integer score;

    public Score(Student student, Subject subject, Integer score) {
        this.student = student;
        this.subject = subject;
        this.score = score;
    }
}