package com.stub.rest_sqlite.service;

public class BugFactNotFoundException extends RuntimeException {
    public BugFactNotFoundException(Integer id) {
        super("Bug fact not found: " + id);
    }
}
