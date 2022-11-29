package com.mainlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class EnvironmentService {

    @Value("${storage.bucket.name}")
    private String bucketName;

    @Autowired
    private Region region;

    @Autowired
    private S3Client s3Client;

    public Region getAwsServiceRegion() {
        return region;
    }

    public S3Client getS3Client() {
        return s3Client;
    }


    public String getResourceBucketName() {
        return bucketName;
    };

    @Cacheable(value = "secretsManagerParam", key = "#secretName")
    public String getStringValue(String secretName) {
        // Get Credential from default credential
        // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder()
                .region(getAwsServiceRegion())
                .build();
        GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();
        GetSecretValueResponse secretValueResponse = secretsManagerClient.getSecretValue(valueRequest);
        String result = secretValueResponse.secretString();
        secretsManagerClient.close();
        return result;
    }
}
