package com.mainlab.repository;

import com.mainlab.model.Content;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentRepository extends BaseRdbDaoSupport {

    public List<Content> selectContentList() {
        return sqlSessionTemplate.selectList("ContentSql.selectContentList");
    }

}
