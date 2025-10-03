package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;
import java.util.Random;

/**
 * Stratégie équilibrée - S'adapte selon la situation.
 * Uses injected Random for deterministic testing.
 */
public class BalancedStrategy implements CombatStrategy {
    private Random random;
    
    public BalancedStrategy() {
        this.random = new Random();
    }
    
    public BalancedStrategy(Random random) {
        this.random = random;
    }
    
    @Override
    public void setRandom(Random random) {
        this.random = random;
    }
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        double actorHealthPercentage = (double) actor.getHealth() / actor.getMaxHealth();
        double targetHealthPercentage = (double) target.getHealth() / target.getMaxHealth();
        
        // Si acteur a peu de PV, se régénérer ou défendre
        if (actorHealthPercentage < 0.3) {
            if (actor.getAbilities().contains("Regeneration") && random.nextDouble() > 0.5) {
                return new UseAbilityCommand(actor, actor, "Regeneration");
            }
            return new DefendCommand(actor);
        }
        
        // Si la cible est faible, attaquer agressivement
        if (targetHealthPercentage < 0.3) {
            if (!actor.getAbilities().isEmpty() && random.nextDouble() > 0.5) {
                return new UseAbilityCommand(actor, target, 
                    actor.getAbilities().get(random.nextInt(actor.getAbilities().size())));
            }
            return new AttackCommand(actor, target);
        }
        
        // Sinon, comportement aléatoire équilibré
        double action = random.nextDouble();
        if (action > 0.7) {
            return new DefendCommand(actor);
        } else if (action > 0.3 && !actor.getAbilities().isEmpty()) {
            return new UseAbilityCommand(actor, target, 
                actor.getAbilities().get(random.nextInt(actor.getAbilities().size())));
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