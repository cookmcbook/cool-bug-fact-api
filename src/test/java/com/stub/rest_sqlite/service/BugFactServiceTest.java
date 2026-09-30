package com.stub.rest_sqlite.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stub.rest_sqlite.entity.BugFact;
import com.stub.rest_sqlite.repository.BugFactRepository;

@ExtendWith(MockitoExtension.class)
class BugFactServiceTest {
    @Mock
    private BugFactRepository repository;

    @InjectMocks
    private BugFactService service;

    @Test
    void updateChangesExistingFact() {
        BugFact bugFact = new BugFact("Old fact");
        when(repository.findById(1)).thenReturn(Optional.of(bugFact));
        when(repository.save(bugFact)).thenReturn(bugFact);

        BugFact updated = service.update(1, "New fact");

        assertThat(updated.getFact()).isEqualTo("New fact");
        verify(repository).save(bugFact);
    }
}
