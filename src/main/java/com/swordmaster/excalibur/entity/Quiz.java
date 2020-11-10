package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.InsertQuizDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="quiz")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quiz extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(targetEntity = AnalysisSession.class)
    @JoinColumn(name="analysisSessionId", nullable = false)
    private AnalysisSession analysisSession;

    @Column
    private String content;

    @Column
    private String example1;

    @Column
    private String example2;

    @Column
    private String example3;

    @Column
    private Integer answer;

    @Column(columnDefinition = "TINYINT", length = 1)
    private Integer isPick;

    public InsertQuizDTO toInsertDTO() {
        return InsertQuizDTO.builder()
                .analysisSessionId(analysisSession.getId())
                .content(content)
                .example1(example1)
                .example2(example2)
                .example3(example3)
                .answer(answer)
                .build();
    }
}
