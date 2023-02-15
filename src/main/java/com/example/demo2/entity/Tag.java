package com.example.demo2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;

    @ManyToMany(mappedBy = "tagList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<Article> articles;
}