package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;
import java.util.Random;

/**
 * Stratégie agressive - Privilégie les attaques et capacités offensives.
 * Uses injected Random for deterministic testing.
 */
public class AggressiveStrategy implements CombatStrategy {
    private Random random;
    
    public AggressiveStrategy() {
        this.random = new Random();
    }
    
    public AggressiveStrategy(Random random) {
        this.random = random;
    }
    
    @Override
    public void setRandom(Random random) {
        this.random = random;
    }
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        // Si le personnage a des capacités offensives, les utiliser (70% chance)
        if (!actor.getAbilities().isEmpty() && random.nextDouble() > 0.3) {
            for (String ability : actor.getAbilities()) {
                if (ability.equals("Super Force") || ability.equals("Pouvoir du Feu") || 
                    ability.equals("Telepathie")) {
                    return new UseAbilityCommand(actor, target, ability);
                }
            }
        }
        
        // Sinon, attaquer normalement
        return new AttackCommand(actor, target);
    }
    
    @Override
    public String getStrategyName() {
        return "⚔ AGRESSIVE";
    }
    
    @Override
    public String getDescription() {
        return "Attaque constamment, utilise les capacités offensives";
    }
} 