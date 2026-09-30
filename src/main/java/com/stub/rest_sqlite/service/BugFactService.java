package com.stub.rest_sqlite.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stub.rest_sqlite.entity.BugFact;
import com.stub.rest_sqlite.repository.BugFactRepository;

@Service
@Transactional
public class BugFactService {
    private final BugFactRepository repository;

    public BugFactService(BugFactRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BugFact> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public BugFact findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BugFactNotFoundException(id));
    }

    public BugFact create(String fact) {
        return repository.save(new BugFact(fact));
    }

    public BugFact update(Integer id, String fact) {
        BugFact bugFact = findById(id);
        bugFact.updateFact(fact);
        return repository.save(bugFact);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new BugFactNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public BugFact findRandom() {
        return repository.findRandomBugFact()
                .orElseThrow(() -> new IllegalStateException("No bug facts are available"));
    }
}
