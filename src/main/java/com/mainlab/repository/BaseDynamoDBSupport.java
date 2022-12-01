package com.mainlab.repository;

import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;

public abstract class BaseDynamoDBSupport {

    @Autowired
    protected DynamoDbClient dynamoDbClient;

    protected abstract String getTableName();

    protected Map<String, AttributeValue> getItem(Map<String, AttributeValue> key) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
        return dynamoDbClient.getItem(getItemRequest).item();
    }

    protected void putItem(Map<String, AttributeValue> item) {
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
        dynamoDbClient.putItem(putItemRequest);
    }
}
