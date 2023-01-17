package com.mainlab.repository.dynamo;

import com.mainlab.common.ObjectConvertService;
import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;

@Repository
public class ProfileRepository extends BaseDynamoDBSupport {

    @Autowired
    private ObjectConvertService objectConvertService;

    public void insertProfileContent(String contentId, UserProfileResponse userProfileResponse) {
        String json = objectConvertService.objToString(userProfileResponse);

        HashMap<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("UUID", AttributeValue.builder().s(contentId).build());
        attributeValueHashMap.put("userProfile", AttributeValue.builder().s(json).build());

        putItem(attributeValueHashMap);
    }

    @Override
    protected String getTableName() {
        return "JinsparkLabProfileTable";
    }
}
