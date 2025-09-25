package rpg.decorator;

import rpg.core.Character;

public class FireResistance extends CharacterDecorator {

    public FireResistance(Character character) {
        super(character);
    }

    @Override
    public String getDescription() {
        return character.getDescription() + " [Fire Resistance: Immune to fire damage]";
    }

    @Override
    public int getPowerLevel() {
        return character.getPowerLevel() + 10;
    }
}