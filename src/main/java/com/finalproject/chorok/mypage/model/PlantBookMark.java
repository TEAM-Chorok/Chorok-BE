package com.finalproject.chorok.mypage.model;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.post.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "plant_book_mark")
@Entity
public class PlantBookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_book_mark_no")
    private Long plantBookMarkNo;

    @ManyToOne
    @JoinColumn(name = "plant_id" ,referencedColumnName = "plant_id")
    private Plant plant;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;




    public PlantBookMark(User user, Plant plant) {
        this.user=user;
        this.plant=plant;
    }
}
