package com.mainlab.repository;

import com.mainlab.model.Content;
import com.mainlab.model.ContentType;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContentRepository extends BaseRdbDaoSupport {

    @Override
    protected String getNamespace() {
        return "ContentSql";
    }

    public List<Content> selectContentList() {
        return sqlSessionTemplate.selectList(getMappedSql("selectContentList"));
    }

    public Content selectContent(int contentId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectContent"), contentId);
    }

    public List<Content> selectContentListByContentType(ContentType contentType) {
        Map<String, Object> parameter = new LinkedHashMap<>();
        parameter.put("contentType", contentType);
        return sqlSessionTemplate.selectList(getMappedSql("selectContentListByContentType"), parameter);
    }

    public void insertContent(Content content) {
        // Auto increment key contentId will be stored to content Object.
        sqlSessionTemplate.insert(getMappedSql("insertContent"), content);
    }

}
