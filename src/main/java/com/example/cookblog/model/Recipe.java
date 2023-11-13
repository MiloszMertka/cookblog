package com.example.cookblog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OrderBy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, length = 2000)
    @EqualsAndHashCode.Exclude
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Ingredient> ingredients = new HashSet<>();

    @Column(nullable = false, length = 5000)
    @EqualsAndHashCode.Exclude
    private String instructions;

    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Image image;

    @EqualsAndHashCode.Exclude
    private Integer preparationTimeInMinutes;

    @EqualsAndHashCode.Exclude
    private Integer portions;

    @EqualsAndHashCode.Exclude
    private Integer calorificValue;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy(clause = "creationTimestamp DESC")
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(optional = false)
    @EqualsAndHashCode.Exclude
    private Category category;

    @CreationTimestamp
    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Instant creationTimestamp;

}
