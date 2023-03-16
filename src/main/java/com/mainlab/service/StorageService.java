package com.mainlab.service;

import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${storage.bucket.name}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;
    @Autowired
    private AppLogService appLogService;

    public S3Client getS3Client() {
        return s3Client;
    }

    public String getResourceBucketName() {
        return bucketName;
    }

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
        S3Client s3Client = getS3Client();
        String keyName = "";
        try {
            keyName = generateRandomPartitionKey() + multipartFile.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(getResourceBucketName())
                    .key(keyName)
                    .build();

            InputStream inputStream = multipartFile.getInputStream();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));
            appLogService.info("object has been uploaded successfully - " + keyName);
            return keyName;
        } catch (IOException e) {
            appLogService.error("Error occurs accessing the bucket - " + getResourceBucketName() + " with key " + keyName
                    + ". Message = " + e.getMessage());
            throw new BaseRuntimeException("Failed to upload Resources", ErrorCode.RESOURCE_UPLOAD_FAIL);
        }
    }

    public void deleteObjectFromS3(String objectPath) {
        S3Client s3Client = getS3Client();
        appLogService.info(objectPath + " has been deleted.");
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(getResourceBucketName())
                .key(objectPath)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
