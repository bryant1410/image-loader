package com.novoda.pxfetcher.demo.analytics;

public class BugsenseApiKeyFailedException extends Exception {

    private final String message;

    public BugsenseApiKeyFailedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
