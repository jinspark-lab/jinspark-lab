package com.mainlab.controller;

import com.mainlab.model.Content;
import com.mainlab.model.ContentType;
import com.mainlab.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = { "/contents" })
public class ContentsController {

    @Autowired
    private ContentService contentService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Content> getContentList(@RequestParam("contentType") ContentType contentType) {
        return contentService.getContentListByType(contentType);
    }

    // FIXME: This is just for Test. After testing it on Prod, it should be removed. (NO USECASE)
//    @ResponseBody
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public Content putContent(@RequestParam("contentType") ContentType contentType) {
//        return contentService.putContent(contentType);
//    }
}
