package com.cacao.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class User extends AbstractAuthenticationToken {

    @Id
    private String id;
    private String imgUrl;
    private String email;
    private String realName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> friends;

    @Transient
    private Jwt token;

    public User(){ super(null); }

    public User(Collection<GrantedAuthority> authorities) {
        super(authorities);
    }

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
        return id;
    }
    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
    @JsonIgnore
    @Override
    public Object getCredentials() {
        return token;
    }
    @JsonIgnore
    @Override
    public Object getDetails() {
        return super.getDetails();
    }
    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return token;
    }
    @JsonIgnore
    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }
    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        super.setAuthenticated(b);
    }

    public void setToken(Jwt token) {
        this.token = token;
    }

    public static User fromJwt(Jwt jwt, Collection<GrantedAuthority> authorities) {
        var user = new User(authorities);
        user.setAuthenticated(true);

        user.setId(jwt.getSubject());
        user.setEmail(jwt.getClaim("email"));
        user.setImgUrl(jwt.getClaim("picture"));
        user.setRealName(jwt.getClaim("name"));

        user.setToken(jwt);

        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
