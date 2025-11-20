package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final MutantService mutantService;

    @Autowired
    public DataInitializer(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostConstruct
    public void initData() {
        // Mutant DNA examples
        mutantService.processDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        mutantService.processDna(new String[]{"AAAA", "AAAA", "AAAA", "AAAA"});

        // Human DNA examples
        mutantService.processDna(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});
        mutantService.processDna(new String[]{"AGAG", "TCTC", "AGAG", "TCTC"});
    }
}
