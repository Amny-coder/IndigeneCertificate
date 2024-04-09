package com.amny.IndigeneshipCertificate.service;

import com.amny.IndigeneshipCertificate.entity.Indigene;
import com.amny.IndigeneshipCertificate.entity.UserStatus;
import com.amny.IndigeneshipCertificate.error.FailedToDeleteIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.FailedToUpdateIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.NoApplicantFound;
import com.amny.IndigeneshipCertificate.error.NotBlankedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface IndigeneService {
    Indigene saveIndigeneInfo(Indigene indigene) throws NotBlankedException;

    List<Indigene> fetchIndigeneInfo() throws NoApplicantFound;

    //Indigene fetchByCertificateNumber(String certificateNumber) throws NoApplicantFound;

    void updateApplicantInfo(Indigene indigene, Long id) throws FailedToUpdateIndigeneshipApplication;

    void deleteIndigeneApplication(Long applicantId) throws FailedToDeleteIndigeneshipApplication;

    UserStatus fetchApplicationStatus(Long userId) throws FailedToUpdateIndigeneshipApplication;

    Indigene fetchById(Long userId) throws NoApplicantFound;

    ByteArrayOutputStream certificateGenerator(Long userId) throws NoApplicantFound, IOException;
}
