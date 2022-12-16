package com.mainlab.repository;

import com.mainlab.model.content.ContentType;
import com.mainlab.model.content.SharableContent;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.exception.ErrorCodes;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SharableRepository extends BaseDynamoDBSupport {

    public SharableContent selectSharableContent(String contentId) {
        HashMap<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("UUID", AttributeValue.builder().s(contentId).build());

        Map<String, AttributeValue> item = getItem(attributeValueHashMap);

        ErrorCodes.checkCondition(item.containsKey("UUID"), ErrorCode.INVALID_CONFIG, "DynamoDB is not configured. Check the item.");
        ErrorCodes.checkCondition(item.containsKey("contentType"), ErrorCode.INVALID_CONFIG, "DynamoDB is not configured. Check the item.");
        ErrorCodes.checkCondition(item.containsKey("userId"), ErrorCode.INVALID_CONFIG, "DynamoDB is not configured. Check the item.");

        SharableContent sharableContent = new SharableContent();
        sharableContent.setContentId(item.get("UUID").s());
        sharableContent.setContentType(ContentType.valueOf(item.get("contentType").s()));
        sharableContent.setUserId(item.get("userId").s());
        return sharableContent;
    }

    public void insertSharableContent(SharableContent sharableContent) {
        HashMap<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("UUID", AttributeValue.builder().s(sharableContent.getContentId()).build());
        attributeValueHashMap.put("userId", AttributeValue.builder().s(sharableContent.getUserId()).build());
        attributeValueHashMap.put("contentType", AttributeValue.builder().s(sharableContent.getContentType().toString()).build());
        attributeValueHashMap.put("contentUrl", AttributeValue.builder().s(sharableContent.getContentType().getContentUrl()).build());
        attributeValueHashMap.put("shared", AttributeValue.builder().bool(sharableContent.isShared()).build());

        putItem(attributeValueHashMap);
    }

    @Override
    protected String getTableName() {
        return "SharableDDB";
    }
}
