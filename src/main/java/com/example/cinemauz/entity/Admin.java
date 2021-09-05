package com.example.cinemauz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(nullable = false,unique = true)
    private String username;

    private String password;

    private String phoneNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role>roles;

}
