package com.amny.IndigeneshipCertificate.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FailedToUpdateIndigeneshipApplication.class)
    public ResponseEntity<ErrorMessage> failedToUpdateIndigeneshipApplication(FailedToUpdateIndigeneshipApplication exception,
                                                                        WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(FailedToDeleteIndigeneshipApplication.class)
    public ResponseEntity<ErrorMessage> failedToDeleteIndigeneshipApplication(FailedToDeleteIndigeneshipApplication exception,
                                                                              WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(NotBlankedException.class)
    public ResponseEntity<ErrorMessage> notBlankedException(NotBlankedException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.EXPECTATION_FAILED,
                exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(message);
    }

    @ExceptionHandler(NoApplicantFound.class)
    public ResponseEntity<ErrorMessage> noApplicantFound(NoApplicantFound exception) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler(FileUploadFailed.class)
    public ResponseEntity<ErrorMessage> fileUploadFailed(FileUploadFailed exception) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(errorMessage);
    }
}
