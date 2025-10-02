package com.rpg.model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;
    private int health;
    private List<String> abilities;

    public Character(String name, int strength, int agility, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.health = 100;
        this.abilities = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStrength() { return strength; }
    public void setStrength(int strength) { this.strength = strength; }
    public int getAgility() { return agility; }
    public void setAgility(int agility) { this.agility = agility; }
    public int getIntelligence() { return intelligence; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public List<String> getAbilities() { return abilities; }
    public void addAbility(String ability) { this.abilities.add(ability); }

    public int getTotalStats() {
        return strength + agility + intelligence;
    }

    public int getPowerLevel() {
        return getTotalStats() + (abilities.size() * 5);
    }

    @Override
    public String toString() {
        return String.format("%s (Force:%d Agilite:%d Intelligence:%d Sante:%d PV) Capacites:%s", 
                           name, strength, agility, intelligence, health, abilities);
    }
}
