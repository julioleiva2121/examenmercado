package org.example.service;

import org.example.model.DnaRecord;
import org.example.model.StatsResponse;
import org.example.repository.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {

    @Mock
    private DnaRepository dnaRepository;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void testIsMutant_HorizontalAndVertical() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    void testIsMutant_Diagonal() {
        String[] dna = {
            "ACTCTG",
            "CACTGT",
            "TCAGTC",
            "TTGACC",
            "CTCTCT",
            "TCTCTC"
        };
        assertTrue(mutantService.isMutant(dna));
    }

    @Test
    void testIsHuman() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantService.isMutant(dna));
    }

    @Test
    void testProcessDna_NewDna() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String dnaString = String.join(",", dna);

        when(dnaRepository.findByDna(dnaString)).thenReturn(Optional.empty());

        boolean result = mutantService.processDna(dna);

        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testProcessDna_ExistingDna() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String dnaString = String.join(",", dna);
        DnaRecord record = new DnaRecord(dnaString, false);

        when(dnaRepository.findByDna(dnaString)).thenReturn(Optional.of(record));

        boolean result = mutantService.processDna(dna);

        assertFalse(result);
        verify(dnaRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    void testGetStats() {
        when(dnaRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = mutantService.getStats();

        assertEquals(40, stats.getCountMutantDna());
        assertEquals(100, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio());
    }
}
