package com.project.creditmanagementbackend.app.controller;

import com.project.creditmanagementbackend.app.service.SeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/seed")
@RequiredArgsConstructor
@Tag(name = "Seed", description = "Operaciones para inicializar datos")
public class SeedController {

    private final SeedService seedService;

    @PostMapping
    @Operation(summary = "Ejecutar todos los Seeders", description = "Inicializa la base de datos con toda la información disponible.")
    public ResponseEntity<Map<String, String>> runAllSeeds() {
        seedService.seedDatabase();
        return ResponseEntity.ok(Map.of("message", "All seeders executed successfully."));
    }

    @PostMapping("/{name}")
    @Operation(summary = "Ejecutar Seeder por nombre")
    public ResponseEntity<Map<String, String>> runSeedByName(@PathVariable("name") String name) {
        seedService.seedByName(name);
        return ResponseEntity.ok(Map.of("message", "Seeder '" + name + "' executed successfully."));
    }
}
