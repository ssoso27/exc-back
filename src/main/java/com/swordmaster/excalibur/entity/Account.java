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
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String picture;

    @Column
    private UserRole role;

    @Column(name = "accessToken")
    private String accessToken;

    @Column(name = "refreshToken")
    private String refreshToken;

    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .email(this.email)
                .name(this.name)
                .role(this.role.getName())
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .build();
    }
}
