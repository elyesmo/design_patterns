package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;

/**
 * Stratégie défensive - Privilégie la défense et la régénération
 */
public class DefensiveStrategy implements CombatStrategy {
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        // Si les PV sont bas, se régénérer ou se défendre
        double healthPercentage = (double) actor.getHealth() / 100.0;
        
        if (healthPercentage < 0.5) {
            // Essayer de se régénérer si possible
            if (actor.getAbilities().contains("Regeneration")) {
                return new UseAbilityCommand(actor, actor, "Regeneration");
            }
            // Sinon se défendre
            return new DefendCommand(actor);
        }
        
        // Si la santé est bonne, alterner entre défense et attaque
        if (Math.random() > 0.6) {
            return new AttackCommand(actor, target);
        } else {
            return new DefendCommand(actor);
        }
    }
    
    @Override
    public String getStrategyName() {
        return "🛡 DÉFENSIVE";
    }
    
    @Override
    public String getDescription() {
        return "Se défend souvent, régénère quand nécessaire";
    }
} 