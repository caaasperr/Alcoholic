package com.caaasperr.Alcoholic.domain.maker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "maker")
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private String type;
    private String description;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCountry(String country) {
        this.country = country;
    }

    public void updateType(String type) {
        this.type = type;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
