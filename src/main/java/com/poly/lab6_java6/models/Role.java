package com.poly.lab6_java6.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    private String role;
}
