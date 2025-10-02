package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;

/**
 * Stratégie équilibrée - S'adapte selon la situation
 */
public class BalancedStrategy implements CombatStrategy {
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        double actorHealthPercentage = (double) actor.getHealth() / 100.0;
        double targetHealthPercentage = (double) target.getHealth() / 100.0;
        
        // Si acteur a peu de PV, se régénérer ou défendre
        if (actorHealthPercentage < 0.3) {
            if (actor.getAbilities().contains("Regeneration") && Math.random() > 0.5) {
                return new UseAbilityCommand(actor, actor, "Regeneration");
            }
            return new DefendCommand(actor);
        }
        
        // Si la cible est faible, attaquer agressivement
        if (targetHealthPercentage < 0.3) {
            if (!actor.getAbilities().isEmpty() && Math.random() > 0.5) {
                return new UseAbilityCommand(actor, target, 
                    actor.getAbilities().get((int)(Math.random() * actor.getAbilities().size())));
            }
            return new AttackCommand(actor, target);
        }
        
        // Sinon, comportement aléatoire équilibré
        double action = Math.random();
        if (action > 0.7) {
            return new DefendCommand(actor);
        } else if (action > 0.3 && !actor.getAbilities().isEmpty()) {
            return new UseAbilityCommand(actor, target, 
                actor.getAbilities().get((int)(Math.random() * actor.getAbilities().size())));
        } else {
            return new AttackCommand(actor, target);
        }
    }
    
    @Override
    public String getStrategyName() {
        return "⚖ ÉQUILIBRÉE";
    }
    
    @Override
    public String getDescription() {
        return "S'adapte à la situation, équilibre attaque et défense";
    }
} 