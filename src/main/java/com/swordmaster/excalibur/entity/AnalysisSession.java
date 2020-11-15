package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.AnalysisSessionDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="analysis_session")
@Builder
@Getter
@Setter
@ToString
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

    public AnalysisSessionDTO toDTO() {
        return AnalysisSessionDTO.builder()
                .id(id)
                .courseId(course.getId())
                .times(times)
                .build();
    }
}
