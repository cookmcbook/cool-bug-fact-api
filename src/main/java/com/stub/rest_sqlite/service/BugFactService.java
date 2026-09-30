package com.stub.rest_sqlite.service;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<BugFact> findPage(String query, String category, Pageable pageable) {
        int page = Math.max(pageable.getPageNumber(), 0);
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        Pageable bounded = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return repository.findByFilters(normalize(query), normalize(category), bounded);
    }

    @Transactional(readOnly = true)
    public BugFact findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BugFactNotFoundException(id));
    }

    public BugFact create(String fact, String category, Set<String> tags) {
        BugFact bugFact = new BugFact(fact);
        bugFact.updateDetails(fact, normalize(category), normalizeTags(tags));
        return repository.save(bugFact);
    }

    public BugFact update(Integer id, String fact, String category, Set<String> tags) {
        BugFact bugFact = findById(id);
        bugFact.updateDetails(fact, normalize(category), normalizeTags(tags));
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

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private Set<String> normalizeTags(Set<String> tags) {
        if (tags == null) {
            return Set.of();
        }
        Set<String> normalized = new LinkedHashSet<>();
        tags.stream().map(String::trim).filter(tag -> !tag.isEmpty()).forEach(normalized::add);
        return normalized;
    }
}
