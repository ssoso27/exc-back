package com.swordmaster.excalibur.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.vo.GoogleOAuthRequest;
import com.swordmaster.excalibur.vo.GoogleOAuthResponse;
import com.swordmaster.excalibur.vo.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleAPIHelper {
    @Value("${google.base_url}")
    private String GOOGLE_TOKEN_BASE_URL;

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.secretKey}")
    private String secretKey;

    public String getJWTToken(String authCode, UserRole userRole) throws JsonProcessingException {
        // HTTP Request를 위한 RESTTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Google OAuth Access Token 요청을 위한 파라미터 세팅
        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(clientId)
                .clientSecret(secretKey)
                .code(authCode)
                .redirectUri("http://localhost:8080/accounts/signin/google" + "?role=" + userRole.getName())
                .grantType("authorization_code").build();


        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //AccessToken 발급 요청
        ResponseEntity<String> resultEntity = restTemplate.postForEntity(GOOGLE_TOKEN_BASE_URL, googleOAuthRequestParam, String.class);

        //Token Request
        System.out.println(resultEntity);
        GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
        });

        //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
        return result.getId_token();
    }

    public GoogleUserInfo decodeJWT(String jwtToken) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", jwtToken).encode().toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        GoogleUserInfo userInfo = mapper.readValue(resultJson, new TypeReference<GoogleUserInfo>(){});

        return userInfo;
    }
}
