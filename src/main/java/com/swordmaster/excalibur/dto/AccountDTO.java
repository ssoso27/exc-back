package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.enumclass.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private String email;
    private String name;
    private String role;
    private String accessToken;
}
