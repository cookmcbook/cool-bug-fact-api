package com.stub.rest_sqlite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BugFactRequest(
        @NotBlank(message = "fact must not be blank")
        @Size(max = 1000, message = "fact must be at most 1000 characters")
        String fact) {
}
