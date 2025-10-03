package com.rpg.builder;

import com.rpg.model.Character;
import com.rpg.singleton.GameSettings;
import com.rpg.validator.CharacterValidator;
import com.rpg.validator.ValidationResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder pattern for creating Character instances with validation.
 * 
 * Contract:
 * - build() returns a valid Character object or throws CharacterBuildException
 * - Default values: level=1, HP=100, no abilities
 * - All validation rules must pass (name, stats ≥ 0, sum ≤ maxStatPoints)
 * - Validation aggregates ALL errors before throwing exception
 */
public class CharacterBuilder {
    private String name;
    private int strength = 10;
    private int agility = 10;
    private int intelligence = 10;
    private CharacterValidator validator;
    private List<Character> existingCharacters = new ArrayList<>();

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

    /**
     * Sets the validator chain to use for validation.
     * @param validator The root of the validation chain
     * @return this builder
     */
    public CharacterBuilder setValidator(CharacterValidator validator) {
        this.validator = validator;
        return this;
    }

    /**
     * Sets the list of existing characters for name uniqueness validation.
     * @param existingCharacters List of existing characters
     * @return this builder
     */
    public CharacterBuilder setExistingCharacters(List<Character> existingCharacters) {
        this.existingCharacters = existingCharacters != null ? existingCharacters : new ArrayList<>();
        return this;
    }

    /**
     * Builds and validates the Character.
     * 
     * @return A valid Character instance
     * @throws CharacterBuildException if validation fails, containing all error messages
     */
    public Character build() throws CharacterBuildException {
        // Pre-construction validation
        List<String> errors = new ArrayList<>();
        
        // Validate basic constraints
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name cannot be null or empty");
        }
        
        if (strength < 0) {
            errors.add("Strength cannot be negative (got: " + strength + ")");
        }
        
        if (agility < 0) {
            errors.add("Agility cannot be negative (got: " + agility + ")");
        }
        
        if (intelligence < 0) {
            errors.add("Intelligence cannot be negative (got: " + intelligence + ")");
        }
        
        int totalStats = strength + agility + intelligence;
        int maxStats = GameSettings.getInstance().getMaxStatPoints();
        if (totalStats > maxStats) {
            errors.add("Total stats (" + totalStats + ") exceeds maximum (" + maxStats + ")");
        }
        
        // Check name uniqueness
        if (name != null && !name.trim().isEmpty()) {
            for (Character existing : existingCharacters) {
                if (existing.getName().equalsIgnoreCase(name.trim())) {
                    errors.add("Name '" + name + "' is already in use");
                    break;
                }
            }
        }
        
        if (!errors.isEmpty()) {
            throw new CharacterBuildException(errors);
        }
        
        // Create the character with default values
        Character character = new Character(name, strength, agility, intelligence);
        // level=1, HP=100, no abilities are set in Character constructor
        
        // Post-construction validation using validator chain if provided
        if (validator != null) {
            ValidationResult result = validator.validateWithErrors(character);
            if (!result.isValid()) {
                throw new CharacterBuildException(result.getErrors());
            }
        }
        
        return character;
    }

    /**
     * Exception thrown when character building fails validation.
     * Contains all aggregated error messages.
     */
    public static class CharacterBuildException extends RuntimeException {
        private final List<String> errors;

        public CharacterBuildException(List<String> errors) {
            super("Character validation failed:\n  - " + String.join("\n  - ", errors));
            this.errors = new ArrayList<>(errors);
        }

        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
    }
}
