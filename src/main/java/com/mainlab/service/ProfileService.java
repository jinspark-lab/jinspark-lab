package com.mainlab.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    public String getProfile() {
        return profile + " : " + driver;
    }
}
