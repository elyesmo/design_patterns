package com.rpg.validator;

import com.rpg.model.Character;
import com.rpg.singleton.GameSettings;

/**
 * Validates character abilities count.
 * Number of abilities must not exceed GameSettings.maxAbilities.
 */
public class AbilitiesValidator extends CharacterValidator {
    
    @Override
    @Deprecated
    public boolean validate(Character character) {
        if (character.getAbilities().size() > GameSettings.getInstance().getMaxAbilities()) {
            return false;
        }
        return callNext(character);
    }

    @Override
    protected ValidationResult doValidate(Character character) {
        GameSettings settings = GameSettings.getInstance();
        int abilityCount = character.getAbilities().size();
        int maxAbilities = settings.getMaxAbilities();
        
        if (abilityCount > maxAbilities) {
            return ValidationResult.failure(
                "Too many abilities (" + abilityCount + "), maximum is " + maxAbilities
            );
        }
        
        return ValidationResult.success();
    }
}
