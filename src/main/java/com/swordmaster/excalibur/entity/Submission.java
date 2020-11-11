package com.swordmaster.excalibur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "submission")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission extends BaseEntity {
    public Submission(Integer accountId, Integer quizId) {
        this.account = Account.builder().id(accountId).build();
        this.quiz = Quiz.builder().id(quizId).build();
        this.submit = null;
        this.isRight = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = Quiz.class)
    @JoinColumn(name = "quizId", nullable = false)
    private Quiz quiz;

    @Column
    private Integer submit;

    @Column(columnDefinition = "TINYINT", length = 1)
    private Integer isRight;

}
