package com.jalbersh.lcs.error;

import java.io.Serializable;

/*
    "error": "Bad Request",
    "exception": "org.springframework.web.bind.UnsatisfiedServletRequestParameterException",
    "message": "Invalid parameter",
    "path": "/api/foo",
    "status": 400,
    "timestamp": 1455287904886
 */
public class GenericException implements Serializable {
    private String error;
    private String exception;
    private String message;
    private String path;
    private String status;
    private String timestamp;
    private String debug_message;

    public GenericException() {}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDebug_message() {
        return debug_message;
    }

    public void setDebug_message(String debug_message) {
        this.debug_message = debug_message;
    }
}
