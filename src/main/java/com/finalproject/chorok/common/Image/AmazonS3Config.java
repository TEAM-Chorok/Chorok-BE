package com.finalproject.chorok.common.Image;



import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

//    @Value("${jobs.build.steps[5].with.aws-access-key-id}")
    private String accessKey="AKIA5EQM35EJGSBEMWF2";

//    @Value("${jobs.build.steps[5].with.aws-secret-access-key}")
    private String secretKey = "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";

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}