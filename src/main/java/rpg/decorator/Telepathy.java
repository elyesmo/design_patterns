package rpg.decorator;

import rpg.core.Character;

public class Telepathy extends CharacterDecorator {

    public Telepathy(Character character) {
        super(character);
    }

    @Override
    public String getDescription() {
        return character.getDescription() + " [Telepathy: Can read minds and communicate telepathically]";
    }

    @Override
    public int getPowerLevel() {
        return character.getPowerLevel() + 20;
    }
}