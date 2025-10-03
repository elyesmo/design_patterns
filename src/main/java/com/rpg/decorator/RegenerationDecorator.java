package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.List;

/**
 * Decorator that adds Regeneration ability (+50 Max HP).
 */
public class RegenerationDecorator extends CharacterDecorator {
    private static final int MAX_HEALTH_BONUS = 50;
    private static final int POWER_BONUS = 5;

    public RegenerationDecorator(Character character) {
        super(character);
    }

    @Override
    public int getMaxHealth() {
        return super.getMaxHealth() + MAX_HEALTH_BONUS;
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + POWER_BONUS;
    }

    @Override
    public List<String> getAbilities() {
        List<String> abilities = super.getAbilities();
        abilities.add("Regeneration");
        return abilities;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " [+Régénération: +50 PV Max]";
    }

    @Override
    public int onAfterDamage(int damage) {
        // Regeneration reduces damage taken
        int reducedDamage = damage - 2;
        return super.onAfterDamage(Math.max(1, reducedDamage));
    }
}

