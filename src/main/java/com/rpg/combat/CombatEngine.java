package com.rpg.combat;

import com.rpg.model.Character;
import java.util.Random;

/**
 * Central combat engine providing single source of truth for damage calculation.
 * Supports deterministic testing via Random injection.
 */
public class CombatEngine {
    private final Random random;
    private static final int BASE_DAMAGE_VARIANCE = 10;
    private static final int DEFENSE_BONUS = 20;

    /**
     * Creates a combat engine with a new random generator.
     */
    public CombatEngine() {
        this(new Random());
    }

    /**
     * Creates a combat engine with a seeded random generator for deterministic tests.
     * @param random The random generator to use
     */
    public CombatEngine(Random random) {
        this.random = random;
    }

    /**
     * Creates a combat engine with a specific seed for deterministic tests.
     * @param seed The seed for the random generator
     */
    public CombatEngine(long seed) {
        this(new Random(seed));
    }

    /**
     * Calculates attack damage based on attacker's strength and character power level.
     * Single source of truth for damage calculation.
     * @param attacker The attacking character
     * @return The damage dealt
     */
    public int calculateAttackDamage(Character attacker) {
        int baseDamage = attacker.getStrength();
        int variance = random.nextInt(BASE_DAMAGE_VARIANCE + 1);
        int powerBonus = attacker.getPowerLevel() / 10;
        return baseDamage + variance + powerBonus;
    }

    /**
     * Calculates defense bonus based on defender's agility.
     * @param defender The defending character
     * @return The defense bonus
     */
    public int calculateDefenseBonus(Character defender) {
        return DEFENSE_BONUS + defender.getAgility() / 2;
    }

    /**
     * Calculates ability damage based on ability type and user stats.
     * @param user The character using the ability
     * @param abilityName The name of the ability
     * @return The damage or healing amount
     */
    public int calculateAbilityEffect(Character user, String abilityName) {
        switch (abilityName) {
            case "Telepathie":
                return user.getIntelligence();
            case "Super Force":
                return user.getStrength() * 2;
            case "Regeneration":
                return 30;
            case "Pouvoir du Feu":
                return user.getStrength() + user.getIntelligence();
            case "Invisibilite":
                return 0; // Defensive ability
            default:
                return 0;
        }
    }

    /**
     * Checks if an attack is a critical hit.
     * @param attacker The attacking character
     * @return true if critical hit
     */
    public boolean isCriticalHit(Character attacker) {
        int critChance = Math.min(30, attacker.getAgility() / 2);
        return random.nextInt(100) < critChance;
    }

    /**
     * Applies damage to a character, ensuring HP doesn't go below 0.
     * @param character The character taking damage
     * @param damage The damage amount
     * @return The actual damage dealt (after clamping)
     */
    public int applyDamage(Character character, int damage) {
        int currentHealth = character.getHealth();
        int newHealth = Math.max(0, currentHealth - damage);
        character.setHealth(newHealth);
        return currentHealth - newHealth;
    }

    /**
     * Applies healing to a character, respecting max HP.
     * @param character The character being healed
     * @param healing The healing amount
     * @return The actual healing applied
     */
    public int applyHealing(Character character, int healing) {
        int currentHealth = character.getHealth();
        int maxHealth = character.getMaxHealth();
        int newHealth = Math.min(maxHealth, currentHealth + healing);
        character.setHealth(newHealth);
        return newHealth - currentHealth;
    }

    /**
     * Gets the underlying Random generator (for advanced use cases).
     * @return The Random generator
     */
    public Random getRandom() {
        return random;
    }
}

