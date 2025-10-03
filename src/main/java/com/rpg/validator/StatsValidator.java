package com.rpg.validator;

import com.rpg.model.Character;
import com.rpg.singleton.GameSettings;
import java.util.ArrayList;
import java.util.List;

/**
 * Validates character statistics.
 * - All stats must be >= 0
 * - Total stats must not exceed GameSettings.maxStatPoints
 */
public class StatsValidator extends CharacterValidator {
    
    @Override
    @Deprecated
    public boolean validate(Character character) {
        int total = character.getTotalStats();
        if (total > GameSettings.getInstance().getMaxStatPoints()) {
            return false;
        }
        return callNext(character);
    }

    @Override
    protected ValidationResult doValidate(Character character) {
        List<String> errors = new ArrayList<>();
        GameSettings settings = GameSettings.getInstance();
        
        // Check individual stats are non-negative
        if (character.getStrength() < 0) {
            errors.add("Strength cannot be negative (got: " + character.getStrength() + ")");
        }
        if (character.getAgility() < 0) {
            errors.add("Agility cannot be negative (got: " + character.getAgility() + ")");
        }
        if (character.getIntelligence() < 0) {
            errors.add("Intelligence cannot be negative (got: " + character.getIntelligence() + ")");
        }
        
        // Check total stats
        int total = character.getTotalStats();
        int maxStats = settings.getMaxStatPoints();
        if (total > maxStats) {
            errors.add("Total stats (" + total + ") exceeds maximum (" + maxStats + ")");
        }
        
        if (errors.isEmpty()) {
            return ValidationResult.success();
        }
        return ValidationResult.failure(errors);
    }
}
