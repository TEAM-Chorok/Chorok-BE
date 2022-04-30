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
@Table(name = "plant_water_cycle")
public class PlantWaterCycle {
    @Id
    @Column(name = "plant_water_cycle_code")
    private String plantWaterCycleCode;
    @Column
    private String plantWaterCycle;
}
