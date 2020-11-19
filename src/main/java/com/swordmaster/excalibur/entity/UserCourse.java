package com.swordmaster.excalibur.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_course")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    @ManyToOne(targetEntity = UserCourseGroup.class)
    @JoinColumn(name = "groupId")
    private UserCourseGroup group;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

}
