package com.finalproject.chorok.Post.repository;

import com.finalproject.chorok.Post.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType,String> {
}
