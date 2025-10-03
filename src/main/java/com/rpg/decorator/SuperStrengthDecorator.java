package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.List;

/**
 * Decorator that adds Super Strength ability (+8 Strength).
 */
public class SuperStrengthDecorator extends CharacterDecorator {
    private static final int STRENGTH_BONUS = 8;
    private static final int POWER_BONUS = 5;

    public SuperStrengthDecorator(Character character) {
        super(character);
    }

    @Override
    public int getStrength() {
        return super.getStrength() + STRENGTH_BONUS;
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + POWER_BONUS;
    }

    @Override
    public List<String> getAbilities() {
        List<String> abilities = super.getAbilities();
        abilities.add("Super Force");
        return abilities;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " [+Super Force: +8 Force]";
    }

    @Override
    public int onBeforeAttack(Character target) {
        // Super strength adds significant damage
        return super.onBeforeAttack(target) + 5;
    }
}

