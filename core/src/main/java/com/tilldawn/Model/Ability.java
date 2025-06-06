package com.tilldawn.Model;

public class Ability {
    private final String name;
    private final String description;

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }

}
