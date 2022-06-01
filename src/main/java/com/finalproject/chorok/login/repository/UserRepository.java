package com.finalproject.chorok.login.repository;



import com.finalproject.chorok.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import software.amazon.ion.Decimal;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByGoogleId(String googleId);
    User findFirstOrderByUsername();


}