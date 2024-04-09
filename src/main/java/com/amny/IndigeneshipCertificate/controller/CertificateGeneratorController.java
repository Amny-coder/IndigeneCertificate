package com.amny.IndigeneshipCertificate.controller;

import com.amny.IndigeneshipCertificate.error.NoApplicantFound;
import com.amny.IndigeneshipCertificate.service.IndigeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class CertificateGeneratorController {

    @Autowired
    private IndigeneService indigeneService;

    @GetMapping("/api/v1/users/indigene-certificate/{userId}")
    public ResponseEntity<byte[]> generateCertificate(@PathVariable("userId") Long userId) throws NoApplicantFound, IOException {
        ByteArrayOutputStream outputStream = indigeneService.certificateGenerator(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "indigene.pdf");

        return new  ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }
}
