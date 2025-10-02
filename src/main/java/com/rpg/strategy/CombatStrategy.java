package com.rpg.strategy;

import com.rpg.model.Character;
import com.rpg.command.Command;

/**
 * Pattern Strategy - Interface pour les différentes stratégies de combat
 */
public interface CombatStrategy {
    Command chooseAction(Character actor, Character target);
    String getStrategyName();
    String getDescription();
} 