package com.rpg.command;

import com.rpg.combat.CombatEngine;
import com.rpg.model.Character;

/**
 * Command for attacking another character.
 * Uses CombatEngine for consistent damage calculation.
 */
public class AttackCommand implements Command {
    private Character attacker;
    private Character target;
    private static CombatEngine combatEngine = new CombatEngine();

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }

    /**
     * Sets a custom combat engine (for testing with seeded Random).
     * @param engine The combat engine to use
     */
    public static void setCombatEngine(CombatEngine engine) {
        combatEngine = engine;
    }

    @Override
    public void execute() {
        int damage = combatEngine.calculateAttackDamage(attacker);
        boolean isCrit = combatEngine.isCriticalHit(attacker);
        
        if (isCrit) {
            damage = (int)(damage * 1.5);
            System.out.println("★ COUP CRITIQUE! ★");
        }
        
        combatEngine.applyDamage(target, damage);
        
        System.out.println(attacker.getName() + " attaque " + target.getName() + 
                         " et inflige " + damage + " dégâts" + (isCrit ? " (critique)" : "") + "!");
        System.out.println(target.getName() + " a maintenant " + target.getHealth() + " PV");
    }

    @Override
    public ActionDTO toDTO() {
        return new ActionDTO("ATTACK")
            .addArg("attacker", attacker.getName())
            .addArg("target", target.getName());
    }

    @Override
    public String toString() {
        return attacker.getName() + " attaque " + target.getName();
    }
}
