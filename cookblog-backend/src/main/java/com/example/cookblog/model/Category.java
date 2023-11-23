package com.example.cookblog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OrderBy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    @OrderBy(clause = "creationTimestamp DESC")
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Recipe> recipes = new ArrayList<>();

}
