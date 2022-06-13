package com.mainlab.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseRdbDaoSupport {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

}
