package com.finalproject.chorok.common.Image;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.finalproject.chorok.common.Image.test.Post1;
import com.finalproject.chorok.common.Image.test.PostRepository1;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final ImageRepository imageRepository;

    private final AmazonS3Client amazonS3Client;

    private final PostRepository1 postRepository;

//    @Value("${cloud.aws.s3.bucket}")
    public String bucket = "miniprojectspring7";  // S3 버킷 이름

//    @Value("${jobs.build.steps[5].with.aws-access-key-id}")
    private String accessKey="AKIA5EQM35EJGSBEMWF2";

//    @Value("${jobs.build.steps[5].with.aws-secret-access-key}")
    private String secretKey="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";

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        removeNewFile(uploadFile);

        Image image = new Image(fileName, uploadImageUrl);
        imageRepository.save(image);

        return uploadImageUrl;
    }

    //게시글 수정 (이미지 파일 변환)
    public String updateImage(MultipartFile multipartFile, String dirName, Long postId)throws IOException{
        File uploadFile = convert(multipartFile)
                .orElseThrow(()->new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return imageUpdate(uploadFile, dirName, postId);
    }

    // 게시글 수정 (이미지 파일 교체)
    private String imageUpdate(File uploadFile, String dirName, Long postId) {
        Post1 post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시물이 없습니다")
        );
        String imageUrl = post.getImageUrl();
        Image image = imageRepository.findByImageUrl(imageUrl);
        String fileName = image.getFilename();
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        return upload(uploadFile, dirName);
    }

    //프로필 이미지 수정
    public String updateProfileImage(MultipartFile multipartFile, String dirName, UserDetailsImpl userDetails)throws IOException{
        File uploadFile = convert(multipartFile)
                .orElseThrow(()->new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        String profileImgUrl = userDetails.getUser().getProfileImageUrl();
        if(profileImgUrl!=null) {
            Image image = imageRepository.findByImageUrl(userDetails.getUser().getProfileImageUrl());
            String fileName = image.getFilename();
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        }
        return upload(uploadFile, dirName);
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public void deleteImage(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}