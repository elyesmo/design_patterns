package com.rpg.builder;

import com.rpg.model.Character;

public class CharacterBuilder {
    private String name;
    private int strength = 10;
    private int agility = 10;
    private int intelligence = 10;

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
        return new Character(name, strength, agility, intelligence);
    }
}
