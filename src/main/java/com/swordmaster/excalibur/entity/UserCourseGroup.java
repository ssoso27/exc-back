package com.swordmaster.excalibur.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_course_group")
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @Column
    private String name;

}
