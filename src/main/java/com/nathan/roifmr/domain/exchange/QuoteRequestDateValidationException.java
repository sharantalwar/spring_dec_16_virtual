package com.nathan.roifmr.domain.exchange;

public class QuoteRequestDateValidationException extends Exception {
    public QuoteRequestDateValidationException() {
    }

    public QuoteRequestDateValidationException(String message) {
        super(message);
    }

    public QuoteRequestDateValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteRequestDateValidationException(Throwable cause) {
        super(cause);
    }

    public QuoteRequestDateValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
