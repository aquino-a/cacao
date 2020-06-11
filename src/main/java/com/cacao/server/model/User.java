package com.cacao.server.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.security.Principal;
import java.util.Collection;

@Entity
public class User implements Authentication {

    @Id
    private String id;
    private String imgUrl;
    private String email;
    private String realName;

    @Transient
    private JwtAuthenticationToken token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getName() {
        return token.getName();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return token.getAuthorities();
    }
    @Override
    public Object getCredentials() {
        return token.getCredentials();
    }
    @Override
    public Object getDetails() {
        return token.getDetails();
    }
    @Override
    public Object getPrincipal() {
        return token.getPrincipal();
    }
    @Override
    public boolean isAuthenticated() {
        return token.isAuthenticated();
    }
    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        token.setAuthenticated(b);
    }

    public void setToken(JwtAuthenticationToken token) {
        this.token = token;
    }

    public static User fromJwtAuthenticationToken(JwtAuthenticationToken token) {
        var jwt = (Jwt) token.getPrincipal();
        var user = new User();

        user.setId(token.getName());
        user.setEmail(jwt.getClaim("email"));
        user.setImgUrl(jwt.getClaim("picture"));
        user.setRealName(jwt.getClaim("name"));

        user.setToken(token);

        return user;
    }
}
