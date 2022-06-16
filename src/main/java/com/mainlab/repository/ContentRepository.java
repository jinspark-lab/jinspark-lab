package com.mainlab.repository;

import com.mainlab.model.Content;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContentRepository extends BaseRdbDaoSupport {

    public List<Content> selectContentList() {
        return sqlSessionTemplate.selectList("ContentSql.selectContentList");
    }

    public List<Content> selectContentListByContentType(String contentType) {
        Map<String, Object> parameter = new LinkedHashMap<>();
        parameter.put("contentType", contentType);
        return sqlSessionTemplate.selectList("ContentSql.selectContentListByContentType", parameter);
    }
}
