package com.mainlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceFileService {

    @Autowired
    private StorageService storageService;

    public String uploadFileToStorage(MultipartFile file) {
        String s3Object = storageService.uploadObjectToS3(file);
        System.out.println(s3Object + " has been uploaded.");
        return s3Object;
    }

}
