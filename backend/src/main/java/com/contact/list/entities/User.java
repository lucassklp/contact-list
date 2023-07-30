package com.contact.list.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(unique=true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy="user")
    private List<Contact> contacts;
}
