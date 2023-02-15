package com.example.demo2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String email;

    private String password;

    private String token;

    @Column(unique = true)
    private String username;

    private String bio;

    private String image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_follow", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> followings;

    @OneToMany(mappedBy = "author")
    private Set<Article> articles;
}
