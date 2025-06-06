package com.poly.lab6_java6.services;


import com.poly.lab6_java6.models.Authority;
import com.poly.lab6_java6.config.CustomUserDetails;
import com.poly.lab6_java6.models.CRUDUserDTO;
import com.poly.lab6_java6.models.Role;
import com.poly.lab6_java6.models.User;
import com.poly.lab6_java6.repositories.AuthorityRepository;
import com.poly.lab6_java6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;


    public List<User> findAll() {
    return	userRepository.findAll();
    }
    public User registerOrUpdate (User user){
       if (!userRepository.findById(user.getEmail()).isPresent()){
           user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
           Authority au = new Authority();
           au.setUser(user);
           au.setRole(new Role("USER"));
           userRepository.save(user);
           authorityRepository.save(au);
       }
        return userRepository.save(user);
    }

    public List<CRUDUserDTO> convertUserToCRUDUserDTO() {
        List<User> users = this.findAll();
     return users.stream().map(user -> new CRUDUserDTO(user.getEmail(), user.getFullname(), user.getPicture(),
             user.getAuthorities().stream().map( role-> role.getRole().getRole()).toList())).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        System.err.println("chạy qua userdetails");
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Không tìm thấy Username này "+ username);
        }
        List<Authority> authorities = authorityRepository.findAllByEmail(username);
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
        for (Authority authority : authorities) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(authority.getRole().getRole()));
        }
        return new CustomUserDetails(user, grantedAuthoritySet);
    }
}
