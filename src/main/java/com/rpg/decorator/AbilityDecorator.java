package com.rpg.decorator;

import com.rpg.model.Character;

public class AbilityDecorator {

    public static void addInvisibility(Character character) {
        character.addAbility("Invisibilite");
        character.setAgility(character.getAgility() + 5);
    }

    public static void addTelepathy(Character character) {
        character.addAbility("Telepathie");
        character.setIntelligence(character.getIntelligence() + 5);
    }

    public static void addSuperStrength(Character character) {
        character.addAbility("Super Force");
        character.setStrength(character.getStrength() + 8);
    }

    public static void addRegeneration(Character character) {
        character.addAbility("Regeneration");
        character.setHealth(character.getHealth() + 50);
    }

    public static void addFirePower(Character character) {
        character.addAbility("Pouvoir du Feu");
        character.setStrength(character.getStrength() + 3);
        character.setIntelligence(character.getIntelligence() + 3);
    }
}
