package com.example.demo2.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String slug;
    private String title;
    private String description;
    private String body;
    private Date createdAt;
    private Date updatedAt;
    private boolean isFavorite;
    private int favoritesCount;

    @ManyToOne
    @JoinColumn(name ="author_id")
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="article_tag", joinColumns = @JoinColumn(name = "article_id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagList;

}
