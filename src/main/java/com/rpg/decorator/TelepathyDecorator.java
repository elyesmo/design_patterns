package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.List;

/**
 * Decorator that adds Telepathy ability (+5 Intelligence).
 */
public class TelepathyDecorator extends CharacterDecorator {
    private static final int INTELLIGENCE_BONUS = 5;
    private static final int POWER_BONUS = 5;

    public TelepathyDecorator(Character character) {
        super(character);
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
        abilities.add("Telepathie");
        return abilities;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " [+Télépathie: +5 Intelligence]";
    }

    @Override
    public int onBeforeAttack(Character target) {
        // Telepathy allows reading opponent's mind for better attacks
        return super.onBeforeAttack(target) + 3;
    }
}

