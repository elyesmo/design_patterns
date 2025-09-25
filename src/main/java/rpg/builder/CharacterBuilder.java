package rpg.builder;

import rpg.core.Character;

public class CharacterBuilder {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;

    public CharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CharacterBuilder setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public CharacterBuilder setAgility(int agility) {
        this.agility = agility;
        return this;
    }

    public CharacterBuilder setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public Character build() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Character name cannot be null or empty");
        }
        return new Character(name, strength, agility, intelligence);
    }

    public CharacterBuilder reset() {
        this.name = null;
        this.strength = 0;
        this.agility = 0;
        this.intelligence = 0;
        return this;
    }
}