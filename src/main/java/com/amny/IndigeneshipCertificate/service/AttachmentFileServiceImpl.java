package com.amny.IndigeneshipCertificate.service;


import com.amny.IndigeneshipCertificate.entity.AttachmentFile;
import com.amny.IndigeneshipCertificate.error.FileUploadFailed;
import com.amny.IndigeneshipCertificate.repository.AttachmentFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class AttachmentFileServiceImpl implements AttachmentFileService {

    private static final long MAX_FILE_SIZE = 250 * 1024;

    @Autowired
    AttachmentFileRepository attachmentFileRepository;

    @Override
    public AttachmentFile saveIdentityFile(MultipartFile file) throws FileUploadFailed, IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        boolean checkExtension = checkFileExtension(fileName);

        try {
            if (!(checkExtension)) {
                throw new FileUploadFailed("File Format Not Supported");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new FileUploadFailed("Upload Failed File Exceed Maximum Size");
            }

            AttachmentFile attachment =
                    new AttachmentFile(
                            fileName,
                            file.getContentType(),
                            file.getBytes()
                    );

            return attachmentFileRepository.save(attachment);

        }catch (FileUploadFailed e) {
            throw new FileUploadFailed("Failed To Upload File " + fileName);
        }

    }

    private boolean checkFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String fileExtension = fileName.substring(index);

        String[] supportedExtensions = {".JPEG", ".JPG", ".PNG", ".SVG"};

        for (String extensions : supportedExtensions) {
            if (extensions.equalsIgnoreCase(fileExtension))
                return true;
        }
        return false;
    }
}
