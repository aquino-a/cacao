package com.cacao.server.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.persistence.Transient;
import java.util.Collection;

public class User implements Authentication {

    private String id;
    private String imgUrl;
    private String email;
    private String name;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    @Transient
    private Object credentials;
    @Transient
    private Object details;
    @Transient
    private Object principal;
    @Transient
    private boolean isAuthenticated;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthenticated = b;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public static User fromJwtAuthenticationToken(JwtAuthenticationToken token) {
        var jwt = (Jwt) token.getPrincipal();
        var user = new User();

        user.setId(token.getName());
        user.setAuthenticated(token.isAuthenticated());
        user.setEmail(jwt.getClaim("email"));
        user.setImgUrl(jwt.getClaim("picture"));
        user.setName(jwt.getClaim("name"));

        user.setAuthorities(token.getAuthorities());
        user.setCredentials(token.getCredentials());
        user.setDetails(token.getDetails());
        user.setPrincipal(token.getPrincipal());

        return user;
    }


    //name, family name, email, id, picture
}
