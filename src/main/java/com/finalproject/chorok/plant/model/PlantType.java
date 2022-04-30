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
@Table(name = "plant_type")
public class PlantType {
    @Id
    @Column(name = "plant_type_code")
    private String plantTypeCode;
    @Column(name = "plant_type")
    private String plantType;
}
