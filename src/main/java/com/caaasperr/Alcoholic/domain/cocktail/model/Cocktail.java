package com.caaasperr.Alcoholic.domain.cocktail.model;

import com.caaasperr.Alcoholic.domain.step.model.Step;
import com.caaasperr.Alcoholic.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cocktail")
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 100)
    @NotNull
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 255)
    @NotNull
    private String cover_image;

    @OneToMany(mappedBy = "cocktail", fetch = FetchType.LAZY)
    private List<CocktailIngredients> ingredients;

    @OneToMany(mappedBy = "cocktail", fetch = FetchType.LAZY)
    private List<CocktailTags> tags;

    @OneToMany(mappedBy = "cocktail", fetch = FetchType.LAZY)
    private List<Step> steps;

    private Float vol;
    private Long view;
    private LocalDateTime created_at;
}
