package com.stub.rest_sqlite.dto;

import com.stub.rest_sqlite.entity.BugFact;
import java.util.Set;

public record BugFactResponse(Integer id, String fact, String category, Set<String> tags) {
    public static BugFactResponse from(BugFact bugFact) {
        return new BugFactResponse(bugFact.getId(), bugFact.getFact(), bugFact.getCategory(), bugFact.getTags());
    }
}
