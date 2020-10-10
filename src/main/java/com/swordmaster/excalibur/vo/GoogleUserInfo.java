package com.swordmaster.excalibur.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserInfo {
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String iat;
    private String exp;
    private String alg;
    private String kid;

    private String email;
    private String email_verified;
    private String at_hash;
    private String name;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String typ;
    private String token;

}
