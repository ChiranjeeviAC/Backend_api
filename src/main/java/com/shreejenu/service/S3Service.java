package com.shreejenu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class S3Service {

    @Autowired
    private S3Presigner s3Presigner;

    @Value("${amazon.aws.s3.bucket}")
    private String bucketName;

    /**
     * Generates a pre-signed URL for uploading a file to S3.
     * @param objectKey The unique key (filename) for the object in S3.
     * @return The pre-signed URL as a String.
     */
    public String generateUploadUrl(String objectKey) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10)) // The URL will be valid for 10 minutes
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presignedUrl = s3Presigner.presignPutObject(presignRequest);
        return presignedUrl.url().toString(); //
    }

    /**
     * Generates a pre-signed URL for downloading a file from S3.
     * @param objectKey The unique key (filename) of the object in S3.
     * @return The pre-signed URL as a String.
     */
    public String generateDownloadUrl(String objectKey) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10)) // The URL will be valid for 10 minutes
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedUrl = s3Presigner.presignGetObject(presignRequest);
        return presignedUrl.url().toString(); //
    }
}