package com.caaasperr.Alcoholic.domain.ingredient.model;

import com.caaasperr.Alcoholic.domain.maker.model.Maker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float vol;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Maker maker;

    private String description;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateVol(Float vol) {
        this.vol = vol;
    }

    public void updateType(String type) {
        this.type = type;
    }

    public void updateMaker(Maker maker) {
        this.maker = maker;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
