package com.caaasperr.Alcoholic.domain.rating.model;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import com.caaasperr.Alcoholic.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id", nullable = false)
    private Cocktail cocktail;

    private String description;

    private Float score;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public void updateScore(Float score) {
        this.score = score;
    }

    public void updateUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
