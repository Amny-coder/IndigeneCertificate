package com.amny.IndigeneshipCertificate.service;

import com.amny.IndigeneshipCertificate.entity.AttachmentFile;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentFileService {
    AttachmentFile saveIdentityFile(MultipartFile file) throws Exception;
}
