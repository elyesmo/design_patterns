package com.rpg.validator;

import com.rpg.model.Character;

/**
 * Validates that character name is not null or empty.
 */
public class NameValidator extends CharacterValidator {
    
    @Override
    @Deprecated
    public boolean validate(Character character) {
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            return false;
        }
        return callNext(character);
    }

    @Override
    protected ValidationResult doValidate(Character character) {
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            return ValidationResult.failure("Character name cannot be null or empty");
        }
        return ValidationResult.success();
    }
}
