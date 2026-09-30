package com.stub.rest_sqlite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "bug_fact")
public class BugFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 1000)
    private String fact;

    @Column(length = 80)
    private String category;

    @ElementCollection
    @CollectionTable(name = "bug_fact_tags", joinColumns = @JoinColumn(name = "bug_fact_id"))
    @Column(name = "tag", nullable = false, length = 40)
    private Set<String> tags = new LinkedHashSet<>();

    protected BugFact() {
    }

    public BugFact(String fact) {
        this.fact = fact;
    }

    public void updateDetails(String fact, String category, Set<String> tags) {
        this.fact = fact;
        this.category = category;
        this.tags.clear();
        this.tags.addAll(tags);
    }

    @Override
    public String toString() {
        return String.format("BugFact[id = %d, fact = '%s']", id, fact);
    }

    public Integer getId() {
        return id;
    }

    public String getFact() {
        return fact;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getTags() {
        return Set.copyOf(tags);
    }
}
