package com.caaasperr.Alcoholic.domain.step.model;

import com.caaasperr.Alcoholic.domain.cocktail.model.Cocktail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "step")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String image;

    @Column(name = "`order`")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateOrder(Integer order) {
        this.order = order;
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
