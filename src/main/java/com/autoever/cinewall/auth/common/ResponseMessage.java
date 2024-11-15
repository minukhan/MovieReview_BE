package com.autoever.cinewall.auth.common;

public interface ResponseMessage {
    String SUCCESS = "Success.";
    String VALIDATION_FAIL = "Validation failed.";
    String SIGN_IN_FAIL = "Login information mismatch.";
    String DATABASE_ERROR = "Database error.";
    String SIGN_OUT_FAIL = "Sign Out failed.";
}