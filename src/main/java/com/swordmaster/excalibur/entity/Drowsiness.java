package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.DrowsinessDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="drowsiness")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
