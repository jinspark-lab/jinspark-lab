package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.content.ContentType;
import com.mainlab.model.content.SharableContent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentLinkRepository extends BaseRdbDaoSupport {

    public SharableContent selectSharableContent(String contentId) {
        //Read it from "selectContentLink"
        // DB side - contentLink. App side - SharableContent
        return sqlSessionTemplate.selectOne(getMappedSql("selectContentLink"), contentId);
    }

    public List<SharableContent> selectSharableContentList(String userId) {
        //selectContentLinkListByUser
        return sqlSessionTemplate.selectList(getMappedSql("selectContentLinkListByUser"), userId);
    }

    public void upsertSharableContent(String userId, String contentId, ContentType contentType, boolean shared) {
        sqlSessionTemplate.insert(getMappedSql("upsertContentLink"),
                ImmutableMap.of("userId", userId, "contentId", contentId, "contentType", contentType, "shared", shared ? 1 : 0));
    }

    public void deleteSharableContent(String contentId) {
        sqlSessionTemplate.delete(getMappedSql("deleteContentLink"), contentId);
    }

    @Override
    protected String getNamespace() {
        return "ContentLinkSql";
    }
}
