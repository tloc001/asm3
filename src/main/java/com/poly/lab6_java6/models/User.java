package com.poly.lab6_java6.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    private String email;
    private String fullname;
    private String password;
    private String picture;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference // Đánh dấu phía cha quản lý dữ liệu này
    private List<Authority> authorities = new ArrayList<Authority>();
}
