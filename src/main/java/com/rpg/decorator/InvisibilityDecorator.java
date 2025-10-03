package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.List;

/**
 * Decorator that adds Invisibility ability (+5 Agility).
 */
public class InvisibilityDecorator extends CharacterDecorator {
    private static final int AGILITY_BONUS = 5;
    private static final int POWER_BONUS = 5;

    public InvisibilityDecorator(Character character) {
        super(character);
    }

    @Override
    public int getAgility() {
        return super.getAgility() + AGILITY_BONUS;
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + POWER_BONUS;
    }

    @Override
    public List<String> getAbilities() {
        List<String> abilities = super.getAbilities();
        abilities.add("Invisibilite");
        return abilities;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " [+Invisibilité: +5 Agilité]";
    }

    @Override
    public int onBeforeAttack(Character target) {
        // Invisibility provides bonus on surprise attacks
        return super.onBeforeAttack(target) + 2;
    }
}

