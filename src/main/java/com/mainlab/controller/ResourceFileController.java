package com.mainlab.controller;

import com.mainlab.model.FileUploadResponse;
import com.mainlab.service.ResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/resource")
public class ResourceFileController extends BaseController {

    @Autowired
    private ResourceFileService resourceService;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileUploadResponse uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        return new FileUploadResponse(resourceService.uploadFileToStorage(multipartFile));
    }
}
