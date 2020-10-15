package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.enumclass.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    @Column(name = "accessToken")
    private String accessToken;

    @CreatedDate
    @Column
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .email(this.email)
                .name(this.name)
                .role(this.role.getName())
                .accessToken(this.accessToken)
                .build();
    }
}
