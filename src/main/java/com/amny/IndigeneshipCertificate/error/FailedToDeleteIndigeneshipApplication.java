package com.amny.IndigeneshipCertificate.error;

public class FailedToDeleteIndigeneshipApplication extends Exception {
    public FailedToDeleteIndigeneshipApplication() {
        super();
    }

    public FailedToDeleteIndigeneshipApplication(String message) {
        super(message);
    }

    public FailedToDeleteIndigeneshipApplication(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToDeleteIndigeneshipApplication(Throwable cause) {
        super(cause);
    }

    protected FailedToDeleteIndigeneshipApplication(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
