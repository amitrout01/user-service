package com.userservice.constants;

public enum ErrorCode {

    SUCCESS("200", "Success"),
    UNAUTHORIZED("401", "Unauthorized"),
    BAD_REQUEST("400", "Bad Request"),
    LOGIN_FAILED("401", "Invalid username or password"),
    TOKEN_INVALID("401", "Token invalid or expired"),
    MISSING_AUTH_HEADER("401", "Missing or invalid Authorization header"),
    USER_ALREADY_EXISTS("400", "Username already exists"),
    DEFAULT_ROLE_NOT_FOUND("500", "Default role not found!"),
    RESOURCE_NOT_FOUND("404", "Requested resource not found"),
    SERVER_ERROR("500", "Internal Server Error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
