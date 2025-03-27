package com.poly.lab6_java6.config;

import com.poly.lab6_java6.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {
	private Optional<User> user;
	private String fullname;
	private Collection<? extends GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	public CustomUserDetails() {
		super();
	}

	public CustomUserDetails(Optional<User> user, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.user = user;
		this.authorities = authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.get().getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
	    return user.get().getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.get().getEnabled();
	}
	public String getFullname() {
		return user.get().getFullname();
	}


}
