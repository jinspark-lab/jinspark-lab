package com.mainlab.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
public class EnvironmentService {

    private Region getServiceRegion() {
        return Region.US_EAST_1;
    }

    // TODO: Set EhCache as per Key
    public String getStringValue(String secretName) {
        // Get Credential from default credential
        // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials.html
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder()
                .region(getServiceRegion())
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
