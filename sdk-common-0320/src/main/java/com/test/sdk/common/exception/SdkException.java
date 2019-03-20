package com.test.sdk.common.exception;


import com.test.sdk.common.util.ErrorCodeTO;

public class SdkException extends Exception {

    private ErrorCodeTO error;

    public SdkException(ErrorCodeTO error) {
        this.error = error;
    }

    public ErrorCodeTO getError() {
        return error;
    }

    public void setError(ErrorCodeTO error) {
        this.error = error;
    }
}
