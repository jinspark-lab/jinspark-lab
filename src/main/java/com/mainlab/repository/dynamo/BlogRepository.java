package com.mainlab.repository.dynamo;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;

@Repository
public class BlogRepository extends BaseDynamoDBSupport {

    public void insertBlogContent(String contentId, String markdown) {
        HashMap<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("UUID", AttributeValue.builder().s(contentId).build());
        attributeValueHashMap.put("markdown", AttributeValue.builder().s(markdown).build());

        putItem(attributeValueHashMap);
    }

    @Override
    protected String getTableName() {
        return "JinsparkLabBlogTable";
    }
}
