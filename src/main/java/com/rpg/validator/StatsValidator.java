package com.rpg.validator;

import com.rpg.model.Character;
import com.rpg.singleton.GameSettings;

public class StatsValidator extends CharacterValidator {
    @Override
    public boolean validate(Character character) {
        int total = character.getTotalStats();
        if (total > GameSettings.getInstance().getMaxStatPoints()) {
            return false;
        }
        return callNext(character);
    }
}
