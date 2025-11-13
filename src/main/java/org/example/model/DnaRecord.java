package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dna_records")
@Data
@NoArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 1024)
    private String dna;

    @Column(nullable = false)
    private boolean isMutant;

    public DnaRecord(String dna, boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }
}
