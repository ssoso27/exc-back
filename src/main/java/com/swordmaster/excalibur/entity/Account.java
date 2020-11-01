package com.swordmaster.excalibur.entity;

import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.enumclass.SignUpType;
import com.swordmaster.excalibur.enumclass.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column
    private SignUpType type;

    @Column
    private String password;

    @Column(name = "accessToken")
    private String accessToken;

    @Column(name = "refreshToken")
    private String refreshToken;

    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .id(this.id)
                .email(this.email)
                .name(this.name)
                .role(this.role.getName())
                .type(this.type.getName())
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .build();
    }
}
