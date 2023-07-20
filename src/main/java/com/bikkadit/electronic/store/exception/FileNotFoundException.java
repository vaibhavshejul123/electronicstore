package com.bikkadit.electronic.store.exception;

import java.io.IOException;

public class FileNotFoundException extends IOException {
    private String userId;

    public FileNotFoundException(String userId) {
        super(String.format("No image file found for Id : %s",userId));
        this.userId = userId;
    }
}
