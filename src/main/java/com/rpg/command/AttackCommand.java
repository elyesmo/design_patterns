package com.rpg.command;

import com.rpg.model.Character;

public class AttackCommand implements Command {
    private Character attacker;
    private Character target;

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute() {
        int damage = attacker.getStrength() + (int)(Math.random() * 10);
        target.setHealth(Math.max(0, target.getHealth() - damage));
        System.out.println(attacker.getName() + " attaque " + target.getName() + 
                         " et inflige " + damage + " degats!");
        System.out.println(target.getName() + " a maintenant " + target.getHealth() + " PV");
    }

    @Override
    public String toString() {
        return attacker.getName() + " attaque " + target.getName();
    }
}
