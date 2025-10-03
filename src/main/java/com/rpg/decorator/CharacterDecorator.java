package com.rpg.decorator;

import com.rpg.model.Character;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract decorator for Character that wraps and enhances a character without modifying it.
 * This is the proper implementation of the Decorator pattern.
 * 
 * Decorators can be stacked: new Invisibility(new FireResistance(character))
 */
public abstract class CharacterDecorator {
    protected Character wrappedCharacter;

    public CharacterDecorator(Character character) {
        this.wrappedCharacter = character;
    }

    // Delegate base methods to wrapped character
    public String getName() {
        return wrappedCharacter.getName();
    }

    public int getStrength() {
        return wrappedCharacter.getStrength();
    }

    public int getAgility() {
        return wrappedCharacter.getAgility();
    }

    public int getIntelligence() {
        return wrappedCharacter.getIntelligence();
    }

    public int getHealth() {
        return wrappedCharacter.getHealth();
    }

    public void setHealth(int health) {
        wrappedCharacter.setHealth(health);
    }

    public int getMaxHealth() {
        return wrappedCharacter.getMaxHealth();
    }

    public int getLevel() {
        return wrappedCharacter.getLevel();
    }

    public int getExperience() {
        return wrappedCharacter.getExperience();
    }

    public void addExperience(int exp) {
        wrappedCharacter.addExperience(exp);
    }

    public void resetHealth() {
        wrappedCharacter.resetHealth();
    }

    public int getTotalStats() {
        return wrappedCharacter.getTotalStats();
    }

    /**
     * Gets the power level. Base implementation delegates to wrapped character.
     * Decorators override this to add their own power bonus.
     */
    public int getPowerLevel() {
        return wrappedCharacter.getPowerLevel();
    }

    /**
     * Gets the list of abilities. Base implementation delegates to wrapped character.
     * Decorators override this to add their ability name.
     */
    public List<String> getAbilities() {
        return new ArrayList<>(wrappedCharacter.getAbilities());
    }

    /**
     * Gets the description of the character with all decorations.
     * Decorators override this to add their description.
     */
    public String getDescription() {
        return wrappedCharacter.toString();
    }

    /**
     * Gets the underlying character (unwrapping all decorators).
     */
    public Character getUnderlyingCharacter() {
        return wrappedCharacter;
    }

    /**
     * Hook called before the character attacks.
     * Can be overridden by decorators to modify behavior.
     * @param target The target of the attack
     * @return Additional damage to add
     */
    public int onBeforeAttack(Character target) {
        return 0;
    }

    /**
     * Hook called after the character takes damage.
     * Can be overridden by decorators to modify behavior.
     * @param damage The damage taken
     * @return Actual damage after reduction
     */
    public int onAfterDamage(int damage) {
        return damage;
    }

    /**
     * Hook called when the character defends.
     * Can be overridden by decorators to modify behavior.
     * @return Defense bonus
     */
    public int onDefend() {
        return 0;
    }
}

