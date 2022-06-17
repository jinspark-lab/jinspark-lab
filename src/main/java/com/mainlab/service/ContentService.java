package com.mainlab.service;

import com.mainlab.model.Content;
import com.mainlab.model.ContentType;
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

    public Content getContent(int contentId) {
        return contentRepository.selectContent(contentId);
    }

    public List<Content> getContentListByType(ContentType contentType) {
        return contentRepository.selectContentListByContentType(contentType);
    }

    public Content putContent(ContentType contentType) {
        Content content = new Content();
        content.setContentType(contentType);
        contentRepository.insertContent(content);
        return content;
    }
}
