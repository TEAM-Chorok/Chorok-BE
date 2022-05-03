package com.finalproject.chorok.Common.Image.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

//    Optional<Post> findByIdAndUserId(Long postId, Long user);
//
//    List<Post> findAllByOrderByCreatedAtDesc();
//

}
