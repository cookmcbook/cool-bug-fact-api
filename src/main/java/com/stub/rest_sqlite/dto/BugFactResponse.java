package com.stub.rest_sqlite.dto;

import com.stub.rest_sqlite.entity.BugFact;

public record BugFactResponse(Integer id, String fact) {
    public static BugFactResponse from(BugFact bugFact) {
        return new BugFactResponse(bugFact.getId(), bugFact.getFact());
    }
}
