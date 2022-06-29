package com.mainlab.repository;

import org.springframework.stereotype.Repository;

@Repository
public class LabRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "LabSql";
    }
}
