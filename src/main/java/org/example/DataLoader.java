package org.example;

import org.example.model.DnaRecord;
import org.example.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final DnaRepository dnaRepository;

    @Autowired
    public DataLoader(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Mutant DNA examples
        DnaRecord mutant1 = new DnaRecord("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG", true);
        DnaRecord mutant2 = new DnaRecord("ATGCGA,CAGTGC,TTATTT,AGACGG,GCGTCA,TCACTG", true);

        // Human DNA examples
        DnaRecord human1 = new DnaRecord("ATGCGA,CAGTGC,TTATGT,AGAAGG,ACCCTA,TCACTG", false);
        DnaRecord human2 = new DnaRecord("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCTCTA,TCACTG", false);

        dnaRepository.saveAll(Arrays.asList(mutant1, mutant2, human1, human2));
    }
}
