package com.finalproject.chorok.common.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finalproject.chorok.common.Image.*;

public interface ImageRepository extends JpaRepository<Image, Long> {
   Image findByImageUrl(String imageUrl);

}
