package com.nathan.roifmr.domain.exchange;

public class QuoteGenerationException extends Exception {
    public QuoteGenerationException() {
    }

    public QuoteGenerationException(String message) {
        super(message);
    }

    public QuoteGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuoteGenerationException(Throwable cause) {
        super(cause);
    }

    public QuoteGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
