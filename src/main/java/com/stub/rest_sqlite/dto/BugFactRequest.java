package com.stub.rest_sqlite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record BugFactRequest(
        @NotBlank(message = "fact must not be blank")
        @Size(max = 1000, message = "fact must be at most 1000 characters")
        String fact,
        @Size(max = 80, message = "category must be at most 80 characters")
        String category,
        @Size(max = 10, message = "at most 10 tags are allowed")
        Set<@NotBlank @Size(max = 40, message = "tag must be at most 40 characters") String> tags) {
}
