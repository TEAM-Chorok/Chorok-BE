package com.finalproject.chorok.login.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@AllArgsConstructor
@Entity // DB 테이블 역할을 합니다.
@Table(name = "user")
public class User {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long userId;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, unique = true)
    private String nickname;


    @Column(nullable = true)
    @Lob //Large Object : 파일이름이 길 경우 대비
    private String profileImageUrl;

    @Column(unique = true)
    private String emailCheckToken;

    @Setter
    private Long kakaoId;
    private String googleId;

    @Builder
    public User(String username, String password, String nickname, String emailCheckToken, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = null;
        this.googleId = null;
        this.emailCheckToken = emailCheckToken;
        this.profileImageUrl = profileImageUrl;

    }

    public User(String username, String password, String nickname, Long kakaoId, String profileImage) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.profileImageUrl = profileImage;

    }

    public User(String username, String password, String nickname, Long kakaoId, String googleId, String profileImage) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.googleId = googleId;
        this.profileImageUrl = profileImage;
    }

    public void changePassword(String tempPassword) {
        this.password = tempPassword;
        System.out.println("유저 비밀번호 바꾸기");
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
        System.out.println("유저 닉네임 바꾸기");
    }

    public void changeProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        System.out.println("유저 프로필 이미지 바꾸기");
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    }



