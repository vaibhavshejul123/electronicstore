package com.bikkadit.electronic.store.exception;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

    private String resource;
    private String field;
    private String value;

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found for %s : %s", resource, field, value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
