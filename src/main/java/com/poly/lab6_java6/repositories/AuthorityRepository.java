package com.poly.lab6_java6.repositories;

import com.poly.lab6_java6.models.Authority;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    @Query("SELECT a FROM Authority a WHERE a.user.email = ?1")
    List<Authority> findAllByEmail(String email);
    @Transactional
    void deleteByUserEmailAndRoleRole(String email, String role);

}
