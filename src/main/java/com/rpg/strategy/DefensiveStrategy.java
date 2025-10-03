package com.rpg.strategy;

import com.rpg.command.*;
import com.rpg.model.Character;
import java.util.Random;

/**
 * Stratégie défensive - Privilégie la défense et la régénération.
 * Uses injected Random for deterministic testing.
 */
public class DefensiveStrategy implements CombatStrategy {
    private Random random;
    
    public DefensiveStrategy() {
        this.random = new Random();
    }
    
    public DefensiveStrategy(Random random) {
        this.random = random;
    }
    
    @Override
    public void setRandom(Random random) {
        this.random = random;
    }
    
    @Override
    public Command chooseAction(Character actor, Character target) {
        // Si les PV sont bas, se régénérer ou se défendre
        double healthPercentage = (double) actor.getHealth() / actor.getMaxHealth();
        
        if (healthPercentage < 0.5) {
            // Essayer de se régénérer si possible
            if (actor.getAbilities().contains("Regeneration")) {
                return new UseAbilityCommand(actor, actor, "Regeneration");
            }
            // Sinon se défendre
            return new DefendCommand(actor);
        }
        
        // Si la santé est bonne, alterner entre défense et attaque (40% attaque, 60% défense)
        if (random.nextDouble() > 0.6) {
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