package com.seojs.debateking.exception;

public class DebateRoomException extends RuntimeException{
    public DebateRoomException() {
        super();
    }

    public DebateRoomException(String message) {
        super(message);
    }

    public DebateRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public DebateRoomException(Throwable cause) {
        super(cause);
    }

    protected DebateRoomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
