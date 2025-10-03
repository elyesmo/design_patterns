package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.List;

/**
 * Decorator that adds Fire Power ability (+3 Strength, +3 Intelligence).
 */
public class FirePowerDecorator extends CharacterDecorator {
    private static final int STRENGTH_BONUS = 3;
    private static final int INTELLIGENCE_BONUS = 3;
    private static final int POWER_BONUS = 5;

    public FirePowerDecorator(Character character) {
        super(character);
    }

    @Override
    public int getStrength() {
        return super.getStrength() + STRENGTH_BONUS;
    }

    @Override
    public int getIntelligence() {
        return super.getIntelligence() + INTELLIGENCE_BONUS;
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + POWER_BONUS;
    }

    @Override
    public List<String> getAbilities() {
        List<String> abilities = super.getAbilities();
        abilities.add("Pouvoir du Feu");
        return abilities;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " [+Pouvoir du Feu: +3 Force, +3 Intelligence]";
    }

    @Override
    public int onBeforeAttack(Character target) {
        // Fire power adds burning damage
        return super.onBeforeAttack(target) + 4;
    }
}

