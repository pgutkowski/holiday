package com.pgutkowski.github.resources;

import org.hibernate.validator.constraints.NotEmpty;

public class ApiKeyRequestBody {

    @NotEmpty
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
