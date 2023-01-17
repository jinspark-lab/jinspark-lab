package com.mainlab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    @Bean(name = "region")
    public Region region() {
        return Region.US_EAST_1;
    }

    @Bean(name = "s3Client")
    public S3Client s3Client() {
        return S3Client.builder().region(region()).build();
    }

    @Bean(name = "dynamoDbClient")
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().region(region()).build();
    }

    @Bean(name = "sqsClient")
    public SqsClient sqsClient() {
        return SqsClient.builder().region(region()).build();
    }
}
