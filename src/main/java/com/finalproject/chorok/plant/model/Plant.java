package com.finalproject.chorok.plant.model;


import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "plant")
public class Plant {
    @Id
    @Column(name = "plant_no",unique = true,nullable = false)
    @Comment("식물번호")
    private Long plantNo;
    @Column
    @Comment("식물이름")
    private String plantName;
    @Column
    @Comment("충고")
    private String plantAdvise;
    @Column
    @Comment("식물종류")
    private String plantTypeCode;
    @Column
    @Comment("식물 유통명")
    private String distbName;
    @Column
    @Comment("식물과명")
    private String fmlName;
    @Column
    @Comment("비료요구사항")
    private String frtlzrInfo;
    @Column
    @Comment("적정온도")
    private String growthTemp;
    @Column
    @Comment("식물형")
    private String plantGrowthShapeCode;
    @Column
    @Comment("적정습도")
    private String plantHumid;
    @Column
    @Comment("식물목")
    private String plantComposition;
    @Column
    @Comment("적정광도")
    private String lightTdemanddo;
    @Column
    @Comment("관리레벨")
    private String plantLevelCode;
    @Column
    @Comment("서식지")
    private String orgPlaceInfo;
    @Column
    @Comment("학명")
    private String plantBneName;
    @Column
    @Comment("불리는 이름")
    private String plantZrName;

    @Column
    @Comment("키우기 좋은 위치")
    private String plantPlaceCode;

    @Column
    @Comment("봄 적정 물주기")
    private String waterCycleSprngCode;

    @Column
    @Comment("여름 적정 물주기")
    private String waterCycleSummerCode;

    @Column
    @Comment("가을 적정 물주기")
    private String waterCycleAutumnCode;

    @Column
    @Comment("겨울 적정 물주기")
    private String waterCycleWinterCode;

    @Column
    @Comment("보편적 적정 물주기")
    private String waterCycleCode;

    @Column
    @Comment("겨울 적정 온도")
    private String winterTemp;





}
