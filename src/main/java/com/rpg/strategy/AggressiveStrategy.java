package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;

/**
 * Stratégie agressive - Privilégie les attaques et capacités offensives
 */
public class AggressiveStrategy implements CombatStrategy {
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        // Si le personnage a des capacités offensives, les utiliser
        if (!actor.getAbilities().isEmpty() && Math.random() > 0.3) {
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