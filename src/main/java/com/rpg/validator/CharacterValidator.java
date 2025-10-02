package com.rpg.validator;

import com.rpg.model.Character;

public abstract class CharacterValidator {
    protected CharacterValidator nextValidator;

    public void setNext(CharacterValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    public abstract boolean validate(Character character);

    protected boolean callNext(Character character) {
        if (nextValidator != null) {
            return nextValidator.validate(character);
        }
        return true;
    }
}
