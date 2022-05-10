package com.finalproject.chorok.plant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "plant_img")
public class PlantImg {
    @Id
    @Column(name = "plant_no")
    private String plantNo;
    @Column(name = "plant_name")
    private String plantName;
    @Column(name = "plant_img_prefix")
    private String plantImgPrefix;
    @Column(name = "plant_img_name")
    private String plantImgName;
    @Column(name = "plant_img_thumb_name")
    private String plantImgThumbName;
}
