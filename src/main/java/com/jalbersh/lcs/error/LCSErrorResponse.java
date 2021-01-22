package com.jalbersh.lcs.error;

import java.io.Serializable;

public class LCSErrorResponse implements Serializable {
    private int error;
    private String message;

    public LCSErrorResponse() {}

    public LCSErrorResponse(int error, String message) {
        super();
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
