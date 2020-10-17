package com.swordmaster.excalibur.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.vo.Jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.access.token.secure.key}")
    private String ACCESS_SECRET_KEY;
    @Value("${jwt.refresh.token.secure.key}")
    private String REFRESH_SECRET_KEY;
    @Value("${jwt.access.token.expire.time}")
    private long ACCESS_EXPIRE_MINUTES;
    @Value("${jwt.refresh.token.expire.time}")
    private long REFRESH_EXPIRE_MINUTES;

    // 토큰 타입
    public enum TOKEN_TYPE {
        ACCESS_TOKEN, REFRESH_TOKEN
    }

    // 토큰 타입 데이터
    private static class TokenTypeData{

        private final String key;
        private final long time;

        public TokenTypeData(String key, long time) {
            super();
            this.key = key;
            this.time = time;
        }

        public String getKey() {
            return key;
        }

        public long getTime() {
            return time;
        }
    }

    private TokenTypeData makeTokenTypeData(TOKEN_TYPE tokenType) {
        String key = null;
        long time = 0;

        if (tokenType == TOKEN_TYPE.ACCESS_TOKEN) {
            key = Encoders.BASE64.encode(this.ACCESS_SECRET_KEY.getBytes());
            time = this.ACCESS_EXPIRE_MINUTES;
        } else if (tokenType == TOKEN_TYPE.REFRESH_TOKEN) {
            key = Encoders.BASE64.encode(this.REFRESH_SECRET_KEY.getBytes());
            time = this.REFRESH_EXPIRE_MINUTES;
        }

        return new TokenTypeData(key, time);
    }

    // 토큰 생성
    private String createToken(Map<String, Object> claims, String subject, TOKEN_TYPE tokenType) {
        TokenTypeData ttd = this.makeTokenTypeData(tokenType);

        LocalDateTime d = LocalDateTime.now().plusMinutes(ttd.getTime());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(Date.from(d.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(Date.from( LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, ttd.getKey())
                .compact();
    }

    // 토큰 생성
    public Jwt generateToken(AccountDTO accountDTO) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map claims = objectMapper.convertValue(accountDTO, Map.class);

        String accessToken = this.createToken(claims, accountDTO.getEmail(), TOKEN_TYPE.ACCESS_TOKEN);
        String refreshToken = this.createToken(claims, accountDTO.getEmail(), TOKEN_TYPE.REFRESH_TOKEN);

        return new Jwt(accessToken, refreshToken);
    }

    // 토큰 파싱
    private Claims parseToken(String token, TokenTypeData ttd) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(ttd.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MissingClaimException e) {
            System.out.println("The parsed JWT did ot have the sub field");
        } catch (JwtException e) {
            System.out.println("Don't trust the JWT");
        }

        return claims;
    }

    // 토큰 만료 확인
    private Boolean isExpired(Date expiration) {
        return expiration.before(new Date());
    }

    // 토큰 검증
    public Boolean validate(String token, String email, TOKEN_TYPE token_type) {
        Claims claims = this.parseToken(token, this.makeTokenTypeData(token_type));
        System.out.println(claims);
        return email.equals(claims.getSubject()) && !isExpired(claims.getExpiration());
    }
}
