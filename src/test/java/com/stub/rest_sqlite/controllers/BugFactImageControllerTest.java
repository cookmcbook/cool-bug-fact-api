package com.stub.rest_sqlite.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.stub.rest_sqlite.service.BugFactImageService;

@WebMvcTest(BugFactImageController.class)
class BugFactImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BugFactImageService imageService;

    @Test
    void imageEndpointReturnsJpeg() throws Exception {
        byte[] image = { 1, 2, 3 };
        when(imageService.renderFact(null)).thenReturn(image);

        mockMvc.perform(get("/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(image));
    }

    @Test
    void imageEndpointAcceptsFactId() throws Exception {
        byte[] image = { 4, 5, 6 };
        when(imageService.renderFact(12)).thenReturn(image);

        mockMvc.perform(get("/image?factId=12"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(image));
    }
}
