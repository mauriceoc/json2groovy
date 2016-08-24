package com.json2groovy

enum ExitCode {
    
    SUCCESS(0), INVALID_JSON(1), FILE_NOT_FOUND(2), GENERAL_ERROR(3)

    int code

    public ExitCode(int code) {
        this.code = code
    }
}