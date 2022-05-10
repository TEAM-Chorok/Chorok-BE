package com.finalproject.chorok.Login.model;

import com.finalproject.chorok.Login.dto.LabelingDto;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@AllArgsConstructor
@Entity // DB 테이블 역할을 합니다.
@Table(name = "labeling")
public class Labeling {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "labeling_id")
    private Long labelingId;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = true)
    private String answer1;

    @Column(nullable = true)
    private String answer2;

    @Column(nullable = true)
    private String answer3;

    @Column(nullable = true)
    private String answer4;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;


    @Builder
    public Labeling(LabelingDto labelingDto) {
        this.answer1 = labelingDto.getAnswer1();
        this.answer2 = labelingDto.getAnswer2();
        this.answer3 = labelingDto.getAnswer3();
        this.answer4 = labelingDto.getAnswer4();

    }

    public Labeling(User registeredUser) {
        this.answer1 = null;
        this.answer2 = null;
        this.answer3 = null;
        this.answer4 = null;
        this.user = registeredUser;

    }

    public void update(LabelingDto labelingDto) {
        this.answer1 = labelingDto.getAnswer1();
        this.answer2 = labelingDto.getAnswer2();
        this.answer3 = labelingDto.getAnswer3();
        this.answer4 = labelingDto.getAnswer4();

    }
}



