package com.amny.IndigeneshipCertificate.controller;

import com.amny.IndigeneshipCertificate.entity.AttachmentFile;
import com.amny.IndigeneshipCertificate.error.FileUploadFailed;
import com.amny.IndigeneshipCertificate.service.AttachmentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AttachmentFileController {

    @Autowired
    private AttachmentFileService attachmentFileService;

    @PostMapping("api/v1/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile[] files) throws FileUploadFailed, Exception {
        for (MultipartFile file : files) {
            attachmentFileService.saveIdentityFile(file);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Files Uploaded Successfully");
    }
}
