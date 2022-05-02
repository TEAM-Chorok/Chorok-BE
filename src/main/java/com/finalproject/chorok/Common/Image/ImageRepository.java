package com.finalproject.chorok.Common.Image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByImageUrl(String imageUrl);

}
