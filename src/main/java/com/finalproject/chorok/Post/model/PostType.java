package com.finalproject.chorok.Post.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * [model] - 게시판 타입 model
 *
 * @class   : PostType
 * @author  : 김주호
 * @since   : 2022.04.03
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
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
