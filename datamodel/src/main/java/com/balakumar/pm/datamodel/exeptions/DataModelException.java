package com.balakumar.pm.datamodel.exeptions;

public class DataModelException extends Exception {

    public DataModelException(String message) {
        super(message);
    }

    public DataModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataModelException(Throwable cause) {
        super(cause);
    }

    public DataModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
