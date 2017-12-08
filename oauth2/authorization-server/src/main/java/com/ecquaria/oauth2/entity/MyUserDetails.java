package com.ecquaria.oauth2.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@IdClass(UserCompositeKey.class)
@Entity
@Table(name="user_tbl")
public class MyUserDetails implements UserDetails{

    private String userDomain;
    private String userId;

    private String username;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails() {
    }

    public MyUserDetails(String userDomain, String userId, String username, String password, boolean enabled) {
        super();
        this.userDomain = userDomain;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public MyUserDetails(String userDomain, String userId, String username, String password, boolean enabled,
                         Collection<? extends GrantedAuthority> authorities) {
        super();
        this.userDomain = userDomain;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Id
    @Column(name="USER_DOMAIN",length = 12)
    public String getUserDomain() {
        return userDomain;
    }

    @Id
    @Column(name="USER_ID",length = 64)
    public String getUserId() {
        return userId;
    }

    @Column(name="PWD",length = 128)
    @Override
    public String getPassword() {
        return password;
    }

    @Column(name = "DISPLAY_NAME",length = 128)
    @Override
    public String getUsername() {
        return username;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
