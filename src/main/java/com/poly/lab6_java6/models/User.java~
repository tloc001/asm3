package com.poly.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
    private String username;

    private String fullname;

    private String password;

    private LocalDate birthday;
    
    private String email;

    private String phone;

    @Column(name = "create_at")
    private LocalDateTime createdAt ;

    @Column(name = "update_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    private String address;
// mặc định là user
    private String role ="USER";

    private Boolean enabled = true;

    
}
