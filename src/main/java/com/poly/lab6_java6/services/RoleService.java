package com.poly.lab6_java6.services;

import com.poly.lab6_java6.models.Role;
import com.poly.lab6_java6.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
