package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.AnalysisSessionDTO;
import com.swordmaster.excalibur.enumclass.SessionStatus;
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

    @Column
    private SessionStatus status;

    public AnalysisSessionDTO toDTO() {
        return AnalysisSessionDTO.builder()
                .id(id)
                .courseId(course.getId())
                .times(times)
                .status(status.getName())
                .build();
    }
}
