package com.rpg.model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;
    private int health;
    private int maxHealth;
    private List<String> abilities;
    private int level;
    private int experience;

    public Character(String name, int strength, int agility, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.abilities = new ArrayList<>();
        this.level = 1;
        this.experience = 0;
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
    public void setHealth(int health) { this.health = Math.min(health, maxHealth); }
    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int maxHealth) { 
        this.maxHealth = maxHealth;
        if (this.health > maxHealth) this.health = maxHealth;
    }
    public List<String> getAbilities() { return abilities; }
    public void addAbility(String ability) { this.abilities.add(ability); }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getExperience() { return experience; }
    public void addExperience(int exp) { 
        this.experience += exp;
        // Niveau up tous les 100 XP
        while (this.experience >= 100) {
            levelUp();
            this.experience -= 100;
        }
    }
    
    private void levelUp() {
        this.level++;
        this.maxHealth += 20;
        this.health = maxHealth;
        this.strength += 2;
        this.agility += 2;
        this.intelligence += 2;
    }
    
    public void resetHealth() {
        this.health = maxHealth;
    }

    public int getTotalStats() {
        return strength + agility + intelligence;
    }

    public int getPowerLevel() {
        return getTotalStats() + (abilities.size() * 5);
    }

    @Override
    public String toString() {
        return String.format("%s [Niv.%d] (Force:%d Agilité:%d Intelligence:%d Santé:%d/%d PV) Capacités:%s", 
                           name, level, strength, agility, intelligence, health, maxHealth, abilities);
    }
}
