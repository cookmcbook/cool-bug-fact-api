package com.stub.rest_sqlite.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stub.rest_sqlite.service.BugFactImageService;

@RestController
public class BugFactImageController {
    private final BugFactImageService imageService;

    public BugFactImageController(BugFactImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] randomBugFactImage() {
        return imageService.renderRandomFact();
    }
}