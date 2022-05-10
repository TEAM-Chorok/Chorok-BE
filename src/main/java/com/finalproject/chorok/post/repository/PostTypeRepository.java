package com.finalproject.chorok.post.repository;

import com.finalproject.chorok.post.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType,String> {
}
