package com.mainlab.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRdbDaoSupport {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    protected SqlSessionTemplate batchSqlSessionTemplate;

    protected abstract String getNamespace();

    protected String getMappedSql(String statement) {
        return getNamespace() + "." + statement;
    }
}
