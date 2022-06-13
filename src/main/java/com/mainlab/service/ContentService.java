package com.mainlab.service;

import com.mainlab.model.Content;
import com.mainlab.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getContentList() {
        return contentRepository.selectContentList();
    }

}
