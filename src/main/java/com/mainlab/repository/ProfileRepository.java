package com.mainlab.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "ProfileSql";
    }
}
