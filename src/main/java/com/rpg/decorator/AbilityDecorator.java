package com.rpg.decorator;

import com.rpg.model.Character;

/**
 * Legacy ability decorator using static methods that modify characters directly.
 * 
 * @deprecated This is NOT a proper decorator pattern implementation.
 *             Use the proper CharacterDecorator subclasses instead:
 *             - InvisibilityDecorator
 *             - TelepathyDecorator  
 *             - SuperStrengthDecorator
 *             - RegenerationDecorator
 *             - FirePowerDecorator
 * 
 * This class is kept for backward compatibility only.
 */
@Deprecated
public class AbilityDecorator {

    /**
     * @deprecated Use new InvisibilityDecorator(character) instead
     */
    @Deprecated
    public static void addInvisibility(Character character) {
        character.addAbility("Invisibilite");
        character.setAgility(character.getAgility() + 5);
    }

    /**
     * @deprecated Use new TelepathyDecorator(character) instead
     */
    @Deprecated
    public static void addTelepathy(Character character) {
        character.addAbility("Telepathie");
        character.setIntelligence(character.getIntelligence() + 5);
    }

    /**
     * @deprecated Use new SuperStrengthDecorator(character) instead
     */
    @Deprecated
    public static void addSuperStrength(Character character) {
        character.addAbility("Super Force");
        character.setStrength(character.getStrength() + 8);
    }

    /**
     * @deprecated Use new RegenerationDecorator(character) instead
     */
    @Deprecated
    public static void addRegeneration(Character character) {
        character.addAbility("Regeneration");
        character.setHealth(character.getHealth() + 50);
    }

    /**
     * @deprecated Use new FirePowerDecorator(character) instead
     */
    @Deprecated
    public static void addFirePower(Character character) {
        character.addAbility("Pouvoir du Feu");
        character.setStrength(character.getStrength() + 3);
        character.setIntelligence(character.getIntelligence() + 3);
    }
}
