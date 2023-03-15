package com.mainlab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResourceFileService {

    @Value("${cloudfront.distribution.url}")
    private String cdnUrl;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AppLogService appLogService;

    /***
     * Uploaded file would be accessed via CDN.
     *
     * @param file
     * @return full path of object via CDN
     */
    public String uploadFileToStorage(MultipartFile file) {
        String s3Object = storageService.uploadObjectToS3(file);
        appLogService.info(s3Object + " has been uploaded.");
        return cdnUrl + "/" + s3Object;
    }

    public void deleteObjectFromStorage(String path) {
        storageService.deleteObjectFromS3(path);
    }

}
