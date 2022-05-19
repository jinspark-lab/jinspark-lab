package com.mainlab.repository;


import com.mainlab.model.MyTable;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyTableRepository {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<MyTable> selectMyTableList() {
        return sqlSessionTemplate.selectList("MyTableSql.selectMyTableList");
    }

}
