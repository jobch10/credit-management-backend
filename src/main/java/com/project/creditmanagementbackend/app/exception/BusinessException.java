package com.project.creditmanagementbackend.app.exception;

public class BusinessException extends ApiException {

    public BusinessException(ApiError error) {
        super(error);
    }
}