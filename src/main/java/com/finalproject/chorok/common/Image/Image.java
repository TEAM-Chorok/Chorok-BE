package com.finalproject.chorok.common.Image;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    @Lob //Large Object : 파일이름이 길 경우 대비
    private String imageUrl;

    public Image(String filename, String imageUrl) {
        this.filename = filename;
        this.imageUrl = imageUrl;
    }
}
