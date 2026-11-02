package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.service.SeedService;
import com.project.creditmanagementbackend.app.service.seeder.DataSeeder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeedServiceImpl implements SeedService {

    private final List<DataSeeder> seeders;

    @Override
    @Transactional
    public void seedDatabase() {
        log.info("Starting full database seeding...");
        seeders.stream()
                .sorted(Comparator.comparingInt(DataSeeder::getOrder))
                .forEach(seeder -> {
                    log.info("Running seeder: {}", seeder.getName());
                    seeder.seed();
                });
        log.info("Database seeding completed.");
    }

    @Override
    @Transactional
    public void seedByName(String name) {
        log.info("Starting seeding for: {}", name);
        seeders.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        DataSeeder::seed,
                        () -> { throw new RuntimeException("Seeder not found: " + name); }
                );
        log.info("Seeding for {} completed.", name);
    }
}