package com.finalproject.chorok.plant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "plant_level")
public class PlantLevel {
    @Id
    @Column(name = "plant_level_code")
    private String plantLevelCode;
    @Column(name = "plant_level")
    private String plantLevel;
}
