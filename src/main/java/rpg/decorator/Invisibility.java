package rpg.decorator;

import rpg.core.Character;

public class Invisibility extends CharacterDecorator {

    public Invisibility(Character character) {
        super(character);
    }

    @Override
    public String getDescription() {
        return character.getDescription() + " [Invisibility: Can become invisible for stealth attacks]";
    }

    @Override
    public int getPowerLevel() {
        return character.getPowerLevel() + 15;
    }
}