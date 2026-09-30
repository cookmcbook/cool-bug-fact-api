package com.stub.rest_sqlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

import com.stub.rest_sqlite.entity.BugFact;

public interface BugFactRepository extends JpaRepository<BugFact, Integer> {
        @Query("""
                        select b from BugFact b
                        where (:query is null or lower(b.fact) like lower(concat('%', :query, '%')))
                            and (:category is null or lower(b.category) = lower(:category))
                        """)
        Page<BugFact> findByFilters(String query, String category, Pageable pageable);

    @Query(value = "SELECT * FROM bug_fact ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<BugFact> findRandomBugFact();
}
