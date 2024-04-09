package com.amny.IndigeneshipCertificate.error;

public class NotBlankedException extends Exception {
    public NotBlankedException() {
        super();
    }

    public NotBlankedException(String message) {
        super(message);
    }

    public NotBlankedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotBlankedException(Throwable cause) {
        super(cause);
    }

    protected NotBlankedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
