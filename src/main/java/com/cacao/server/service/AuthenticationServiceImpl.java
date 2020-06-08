package com.cacao.server.service;

import com.cacao.server.model.Tokens;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token" +
            "?code={code}" +
            "&redirect_uri={redirect_uri}" +
            "&client_id={client_id}" +
            "&client_secret={client_secret}" +
            "&scope=" +
            "&grant_type=authorization_code";

    @Autowired()
    @Qualifier("googleTokenParams")
    private Map<String, String> TOKEN_PARAMS;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Tokens retrieveTokens(String authCode) {
        try {
            var params = new HashMap<String,String>(TOKEN_PARAMS);
            params.put("code", authCode);

            var tokenResponse = restTemplate.postForObject(TOKEN_ENDPOINT, null, TokenResponse.class, params);
            var tokens = new Tokens();
            tokens.setAccessToken(tokenResponse.getAccessToken());
            tokens.setIdToken(tokenResponse.getIdToken());
            return tokens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class TokenResponse {

        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("id_token")
        private String idToken;
        @JsonProperty("expires_in")
        private int expires;
        @JsonProperty("token_type")
        private String tokenType;
        private String scope;
        @JsonProperty("refresh_token")
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public int getExpires() {
            return expires;
        }

        public void setExpires(int expires) {
            this.expires = expires;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
