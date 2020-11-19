package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.DrowsinessDTO;
import com.swordmaster.excalibur.dto.DrowsinessForRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="drowsiness")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Drowsiness extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = AnalysisSession.class)
    @JoinColumn(name = "analysisSessionId")
    private AnalysisSession analysisSession;

    @Column
    private String status;

    @Column
    private Integer startSecond;

    @Column
    private Integer endSecond;

    public DrowsinessDTO toDTO() {
        return DrowsinessDTO.builder()
                .id(id)
                .accountId(account.getId())
                .analysisSessionId(analysisSession.getId())
                .status(status)
                .startSecond(startSecond)
                .endSecond(endSecond)
                .build();
    }

    public DrowsinessForRecordDTO toForRecordDTO() {
        return DrowsinessForRecordDTO.builder()
                .id(id)
                .status(status)
                .startSecond(startSecond)
                .endSecond(endSecond)
                .build();
    }

}
