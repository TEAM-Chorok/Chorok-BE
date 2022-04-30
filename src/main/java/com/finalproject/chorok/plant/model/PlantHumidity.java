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
@Table(name = "plant_humidity")
public class PlantHumidity {
    @Id
    @Column(name = "plant_humid_code")
    private String plantHumidCode;
    @Column(name = "plant_humid")
    private String plantHumid;
}
