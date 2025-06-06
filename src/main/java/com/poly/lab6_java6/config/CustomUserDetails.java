package com.poly.lab6_java6.config;


import com.poly.lab6_java6.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {
    Optional<User> user ;
    Collection<? extends GrantedAuthority> authorities ;
    public CustomUserDetails (Optional<User> user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return user.get().getPassword();
    }

    @Override
    public String getUsername() {
        return user.get().getEmail();
    }
}
