package com.mainlab.service;

import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@Service
public class StorageService {

    @Autowired
    private EnvironmentService environmentService;

    private String generateRandomPartitionKey() {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        String randomString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(32)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return randomString + "/";
    }

    public String uploadObjectToS3(MultipartFile multipartFile) {
        S3Client s3Client = environmentService.getS3Client();
        try {
            String keyName = generateRandomPartitionKey() + multipartFile.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(environmentService.getResourceBucketName())
                    .key(keyName)
                    .build();

            InputStream inputStream = multipartFile.getInputStream();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));

            return keyName;
        } catch (IOException e) {
            throw new BaseRuntimeException("Failed to upload Resources", ErrorCode.RESOURCE_UPLOAD_FAIL);
        }
    }

    public void deleteObjectFromS3(String objectPath) {
        S3Client s3Client = environmentService.getS3Client();
        System.out.println(objectPath + " has been deleted.");
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(environmentService.getResourceBucketName())
                .key(objectPath)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
