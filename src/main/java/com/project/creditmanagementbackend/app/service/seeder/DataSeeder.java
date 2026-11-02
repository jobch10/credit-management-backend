package com.project.creditmanagementbackend.app.service.seeder;

public interface DataSeeder {
    /**
     * Name of the seeder, used to identify it in the API.
     */
    String getName();

    /**
     * Execution order. Lower numbers run first.
     */
    int getOrder();

    /**
     * Executes the seeding logic.
     */
    void seed();
}
