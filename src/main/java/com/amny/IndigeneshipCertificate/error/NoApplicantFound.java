package com.amny.IndigeneshipCertificate.error;

public class NoApplicantFound extends Exception {
    public NoApplicantFound() {
        super();
    }

    public NoApplicantFound(String message) {
        super(message);
    }

    public NoApplicantFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoApplicantFound(Throwable cause) {
        super(cause);
    }

    protected NoApplicantFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
