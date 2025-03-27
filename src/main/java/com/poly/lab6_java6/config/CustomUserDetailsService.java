package com.poly.lab6_java6.config;

import com.poly.lab6_java6.models.User;
import com.poly.lab6_java6.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//custom lại userService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.get().getRole()));
        return new CustomUserDetails(user, grantedAuthoritySet);
    }
}
