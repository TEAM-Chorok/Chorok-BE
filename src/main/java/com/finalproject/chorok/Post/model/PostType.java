package com.finalproject.chorok.Post.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_type")
public class PostType {

    @Id
    @Column(name = "post_type_code",unique = true,nullable = false)
    private String postTypeCode;

    @Column(nullable = false)
    private String postType;
}
