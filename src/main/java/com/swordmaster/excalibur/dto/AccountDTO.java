package com.swordmaster.excalibur.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {
    private String email;
    private String name;
    private String role; // TODO: enum화
    private String accessToken;
}
