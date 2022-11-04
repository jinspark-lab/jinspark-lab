package com.mainlab.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class EnvironmentService {

    @Value("${storage.bucket.name}")
    private String bucketName;

    public Region getAwsServiceRegion() {
        return Region.US_EAST_1;
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
