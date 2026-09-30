package com.stub.rest_sqlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import com.stub.rest_sqlite.entity.BugFact;

public interface BugFactRepository extends JpaRepository<BugFact, Integer> {
    @Query(value = "SELECT * FROM bug_fact ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<BugFact> findRandomBugFact();
}
