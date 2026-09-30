package com.stub.rest_sqlite.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bug_fact")
public class BugFact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 1000)
    private String fact;

    protected BugFact() {
    }

    public BugFact(String fact) {
        this.fact = fact;
    }

    public void updateFact(String fact) {
        this.fact = fact;
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
}
