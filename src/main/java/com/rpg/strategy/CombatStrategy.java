package com.rpg.strategy;

import com.rpg.model.Character;
import com.rpg.command.Command;
import com.rpg.combat.CombatEngine;
import java.util.Random;

/**
 * Pattern Strategy - Interface pour les différentes stratégies de combat.
 * Supports Random injection for deterministic testing.
 */
public interface CombatStrategy {
    /**
     * Chooses an action for the actor to perform against the target.
     * @param actor The character performing the action
     * @param target The target character
     * @return The command to execute
     */
    Command chooseAction(Character actor, Character target);
    
    /**
     * Sets the random generator for deterministic behavior.
     * @param random The random generator to use
     */
    void setRandom(Random random);
    
    /**
     * Gets the strategy name.
     * @return The strategy name
     */
    String getStrategyName();
    
    /**
     * Gets the strategy description.
     * @return The strategy description
     */
    String getDescription();
} 