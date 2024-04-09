package com.amny.IndigeneshipCertificate.error;

public class FailedToUpdateIndigeneshipApplication extends Exception {

    public FailedToUpdateIndigeneshipApplication() {
        super();
    }

    public FailedToUpdateIndigeneshipApplication(String message) {
        super(message);
    }

    public FailedToUpdateIndigeneshipApplication(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToUpdateIndigeneshipApplication(Throwable cause) {
        super(cause);
    }

    protected FailedToUpdateIndigeneshipApplication(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
