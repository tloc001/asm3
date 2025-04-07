package com.poly.lab6_java6.services;

import com.poly.lab6_java6.models.Authority;
import com.poly.lab6_java6.models.Role;
import com.poly.lab6_java6.models.User;
import com.poly.lab6_java6.repositories.AuthorityRepository;
import com.poly.lab6_java6.repositories.RoleRepository;
import com.poly.lab6_java6.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public List<Authority> findAllByEmail(String email){
        return authorityRepository.findAllByEmail(email);
    }
    public List<Authority> findAll(){
        return authorityRepository.findAll();
    }
    // Thêm quyền
    public void addAuthority(String email, String roleName) {
        User user = userRepository.findById(email).orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Role role = roleRepository.findById(roleName).orElseThrow(() -> new RuntimeException("Không tìm thấy role"));

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setRole(role);

        authorityRepository.save(authority);
    }
    @Transactional
    // Xóa quyền
    public void removeAuthority(String email, String roleName) {
        authorityRepository.deleteByUserEmailAndRoleRole(email, roleName);
    }

}
