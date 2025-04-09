package com.poly.lab6_java6.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CRUDUserDTO {
    private String email;
    private String fullname;
    private String picture;
    private List<String> roles = new ArrayList<>();
}
