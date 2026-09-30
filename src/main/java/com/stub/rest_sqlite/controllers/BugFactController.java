package com.stub.rest_sqlite.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import jakarta.validation.Valid;

import com.stub.rest_sqlite.dto.BugFactRequest;
import com.stub.rest_sqlite.dto.BugFactResponse;
import com.stub.rest_sqlite.service.BugFactService;

@RestController
@RequestMapping("bug-facts")
public class BugFactController {
    private final BugFactService service;

    public BugFactController(BugFactService service) {
        this.service = service;
    }

    @GetMapping
    public List<BugFactResponse> getAllBugFacts() {
        return service.findAll().stream().map(BugFactResponse::from).toList();
    }

    @GetMapping("/{id}")
    public BugFactResponse getBugFact(@PathVariable Integer id) {
        return BugFactResponse.from(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BugFactResponse addBugFact(@Valid @RequestBody BugFactRequest request) {
        return BugFactResponse.from(service.create(request.fact()));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllBugFacts() {
        service.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBugFactById(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public BugFactResponse updateBugFactById(@PathVariable Integer id,
            @Valid @RequestBody BugFactRequest request) {
        return BugFactResponse.from(service.update(id, request.fact()));
    }
}
