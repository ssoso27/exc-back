package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="course")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name="accountId", nullable = false)
    private Account account;

    public CourseDTO toDTO() {
        return CourseDTO.builder()
                .name(name)
                .createrId(id)
                .build();
    }
}
