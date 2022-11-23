package com.mainlab.controller;

import com.mainlab.model.content.ContentLinkResponse;
import com.mainlab.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/content")
public class ContentController extends BaseController {

    @Autowired
    private ContentService contentService;

    /***
     * Generate Content Link for Profile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/linkProfile", method = RequestMethod.POST)
    public ContentLinkResponse linkProfileContent() {
        return new ContentLinkResponse(contentService.linkProfileContent());
    }

    /***
     * Generate Content Link for UserApp
     * @param appId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/linkUserApp", method = RequestMethod.POST)
    public ContentLinkResponse linkUserAppContent(@RequestParam(value = "appId") String appId) {
        return new ContentLinkResponse(appId);
    }
}
