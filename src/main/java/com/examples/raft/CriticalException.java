package com.examples.raft;

public class CriticalException extends RuntimeException {

    public CriticalException(final String message) {
        super(message);
    }

    public CriticalException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CriticalException(final Throwable cause) {
        super(cause);
    }
}
