package rpg.decorator;

import rpg.core.Character;

public abstract class CharacterDecorator extends Character {
    protected Character character;

    public CharacterDecorator(Character character) {
        super(character.getName(), character.getStrength(), 
              character.getAgility(), character.getIntelligence());
        this.character = character;
    }

    @Override
    public String getDescription() {
        return character.getDescription();
    }

    @Override
    public int getPowerLevel() {
        return character.getPowerLevel();
    }
}