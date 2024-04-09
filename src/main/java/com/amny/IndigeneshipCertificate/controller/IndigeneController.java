package com.amny.IndigeneshipCertificate.controller;

import com.amny.IndigeneshipCertificate.entity.Indigene;
import com.amny.IndigeneshipCertificate.entity.UserStatus;
import com.amny.IndigeneshipCertificate.error.FailedToDeleteIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.FailedToUpdateIndigeneshipApplication;
import com.amny.IndigeneshipCertificate.error.NoApplicantFound;
import com.amny.IndigeneshipCertificate.error.NotBlankedException;
import com.amny.IndigeneshipCertificate.service.IndigeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class IndigeneController {

    @Autowired
    private IndigeneService indigeneService;

    @PostMapping("/save/applications")
    public Indigene saveIndigenedInfo(@RequestBody Indigene indigene) throws NotBlankedException {
        return indigeneService.saveIndigeneInfo(indigene);
    }

    @GetMapping("/applications")
    public List<Indigene> fetchIndigeneInfo() throws NoApplicantFound {
        return indigeneService.fetchIndigeneInfo();
    }

    @GetMapping("/applications/{userId}")
    public Indigene fetchApplicantById(@PathVariable("userId") Long userId) throws NoApplicantFound {
        return indigeneService.fetchById(userId);
    }

    /*@GetMapping("/applications/{certificateNumber}")
    public Indigene fetchApplicantByCertificateNumber(@PathVariable("certificateNumber") String certificateNumber)
            throws NoApplicantFound {
        return indigeneService.fetchByCertificateNumber(certificateNumber);
    }*/

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateInfo(@RequestBody Indigene indigene,
                                             @PathVariable("userId") Long userId) throws FailedToUpdateIndigeneshipApplication {
        indigeneService.updateApplicantInfo(indigene, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("You Successfully Update An Application With ID: " + userId);
    }

    @DeleteMapping("/delete/applications/{userId}")
    public ResponseEntity<String> deleteApplication(@PathVariable("userId") Long userId) throws FailedToDeleteIndigeneshipApplication {
        indigeneService.deleteIndigeneApplication(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("You Successfully Delete An Application With ID: " + userId);
    }

    @GetMapping("{userId}/status")
    public ResponseEntity<String> applicationStatus(@PathVariable("userId") Long userId)
            throws FailedToUpdateIndigeneshipApplication {
        String status = String.valueOf(indigeneService.fetchApplicationStatus(userId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(status);
    }
}
