package com.cacao.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.persistence.*;
import java.security.Principal;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements Authentication {

    @Id
    private String id;
    private String imgUrl;
    private String email;
    private String realName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> friends;

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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return token.getName();
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return token.getAuthorities();
    }
    @JsonIgnore
    @Override
    public Object getCredentials() {
        return token.getCredentials();
    }
    @JsonIgnore
    @Override
    public Object getDetails() {
        return token.getDetails();
    }
    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return token.getPrincipal();
    }
    @JsonIgnore
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
