package org.example.service;

import org.example.model.DnaRecord;
import org.example.model.StatsResponse;
import org.example.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MutantService {

    private final DnaRepository dnaRepository;

    @Autowired
    public MutantService(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    @Transactional
    public boolean processDna(String[] dna) {
        String dnaString = String.join(",", dna);
        Optional<DnaRecord> existingRecord = dnaRepository.findByDna(dnaString);

        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        boolean isMutantResult = isMutant(dna);
        DnaRecord newRecord = new DnaRecord(dnaString, isMutantResult);
        dnaRepository.save(newRecord);
        return isMutantResult;
    }

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }
        int n = dna.length;
        int sequenceCount = 0;

        // Check horizontal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (dna[i].charAt(j) == dna[i].charAt(j + 1) &&
                    dna[i].charAt(j) == dna[i].charAt(j + 2) &&
                    dna[i].charAt(j) == dna[i].charAt(j + 3)) {
                    sequenceCount++;
                }
            }
        }

        // Check vertical
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= n - 4; i++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j)) {
                    sequenceCount++;
                }
            }
        }

        // Check diagonal from top-left to bottom-right
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j + 1) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j + 2) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j + 3)) {
                    sequenceCount++;
                }
            }
        }

        // Check diagonal from top-right to bottom-left
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 3; j < n; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j - 1) &&
                    dna[i].charAt(j) == dna[i + 2].charAt(j - 2) &&
                    dna[i].charAt(j) == dna[i + 3].charAt(j - 3)) {
                    sequenceCount++;
                }
            }
        }

        return sequenceCount > 1;
    }

    @Transactional(readOnly = true)
    public StatsResponse getStats() {
        long mutantCount = dnaRepository.countByIsMutant(true);
        long humanCount = dnaRepository.countByIsMutant(false);
        double ratio = (humanCount == 0) ? 0 : (double) mutantCount / humanCount;
        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
