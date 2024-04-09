package com.amny.IndigeneshipCertificate.error;

public class FileUploadFailed extends Exception {
    public FileUploadFailed() {
        super();
    }

    public FileUploadFailed(String message) {
        super(message);
    }

    public FileUploadFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadFailed(Throwable cause) {
        super(cause);
    }

    protected FileUploadFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
