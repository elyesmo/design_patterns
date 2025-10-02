package com.rpg.validator;

import com.rpg.model.Character;
import com.rpg.singleton.GameSettings;

public class AbilitiesValidator extends CharacterValidator {
    @Override
    public boolean validate(Character character) {
        if (character.getAbilities().size() > GameSettings.getInstance().getMaxAbilities()) {
            return false;
        }
        return callNext(character);
    }
}
