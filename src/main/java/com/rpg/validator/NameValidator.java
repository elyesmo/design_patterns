package com.rpg.validator;

import com.rpg.model.Character;

public class NameValidator extends CharacterValidator {
    @Override
    public boolean validate(Character character) {
        if (character.getName() == null || character.getName().trim().isEmpty()) {
            return false;
        }
        return callNext(character);
    }
}
