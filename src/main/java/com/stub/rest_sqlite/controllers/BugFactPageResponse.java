package com.stub.rest_sqlite.controllers;

import java.util.List;

import org.springframework.data.domain.Page;

import com.stub.rest_sqlite.dto.BugFactResponse;

public record BugFactPageResponse(
        List<BugFactResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static BugFactPageResponse from(Page<BugFactResponse> facts) {
        return new BugFactPageResponse(
                facts.getContent(),
                facts.getNumber(),
                facts.getSize(),
                facts.getTotalElements(),
                facts.getTotalPages());
    }
}
