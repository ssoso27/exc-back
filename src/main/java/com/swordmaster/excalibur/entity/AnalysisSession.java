package com.swordmaster.excalibur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="analysis_session")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisSession extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    @Column
    private Integer times;
}
