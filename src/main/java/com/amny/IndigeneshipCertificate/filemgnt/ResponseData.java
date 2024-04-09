package com.amny.IndigeneshipCertificate.filemgnt;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {

    private String fileName;

    private String fileType;

    private long fileSize;
}
