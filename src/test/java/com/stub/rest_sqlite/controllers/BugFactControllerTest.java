package com.stub.rest_sqlite.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.stub.rest_sqlite.entity.BugFact;
import com.stub.rest_sqlite.service.BugFactNotFoundException;
import com.stub.rest_sqlite.service.BugFactService;

@WebMvcTest(BugFactController.class)
class BugFactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BugFactService service;

    @Test
    void getAllReturnsFacts() throws Exception {
        BugFact fact = new BugFact("Use tests.");
        when(service.findPage(any(), any(), any())).thenReturn(new PageImpl<>(List.of(fact)));

        mockMvc.perform(get("/bug-facts"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].fact").value("Use tests."))
            .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void invalidFactIsRejected() throws Exception {
        mockMvc.perform(post("/bug-facts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fact\":\"  \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid request"));

        verify(service, never()).create(anyString(), any(), any());
    }

    @Test
    void missingFactReturnsNotFound() throws Exception {
        when(service.findById(99)).thenThrow(new BugFactNotFoundException(99));

        mockMvc.perform(get("/bug-facts/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Bug fact not found"));
    }

    @Test
    void searchAndMetadataAreAccepted() throws Exception {
        when(service.findPage(any(), any(), any())).thenReturn(new PageImpl<>(List.of(new BugFact("Java fact"))));

        mockMvc.perform(get("/bug-facts?q=java&category=backend&page=0&size=5"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$.size").value(1))
            .andExpect(jsonPath("$.totalElements").value(1));
    }
}
