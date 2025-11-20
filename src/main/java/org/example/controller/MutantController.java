package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.model.DnaRequest;
import org.example.model.StatsResponse;
import org.example.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MutantController {

    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @Operation(summary = "Detect if a human is a mutant based on DNA sequence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The human is a mutant"),
            @ApiResponse(responseCode = "403", description = "The human is not a mutant")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "DNA sequence to analyze",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Mutant DNA",
                                    value = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}"
                            ),
                            @ExampleObject(
                                    name = "Human DNA",
                                    value = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATTT\",\"AGACGG\",\"GCGTCA\",\"TCACTG\"]}"
                            )
                    }
            )
    ) @RequestBody DnaRequest dnaRequest) {
        boolean isMutant = mutantService.processDna(dnaRequest.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Get statistics of mutant and human DNA checks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics")
    })
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = mutantService.getStats();
        return ResponseEntity.ok(stats);
    }
}
