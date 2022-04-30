package com.finalproject.chorok.plant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "plant_growth_shape")

public class PlantGrowthShape {
    @Id
    @Column(name = "plant_growth_shape_code",unique = true,nullable = false)
    private String plantGrowthShapeCode;
    @Column
    private String plantGrowthShape;

}
