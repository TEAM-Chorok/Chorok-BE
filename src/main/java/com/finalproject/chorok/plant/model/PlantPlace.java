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
@Table(name = "plant_place")
public class PlantPlace {
    @Id
    @Column(name = "plant_place_code")
    private String plantPlaceCode;
    @Column
    private String plantPlace;
}
