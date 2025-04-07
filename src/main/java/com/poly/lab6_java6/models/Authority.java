package com.poly.lab6_java6.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="authorities")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "email")
    @JsonBackReference // Đánh dấu phía con không cần serialize lại đối tượng cha
    private User user;
    @ManyToOne
    @JoinColumn(name = "role")

    private Role role;


}
