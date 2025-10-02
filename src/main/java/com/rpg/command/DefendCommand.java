package com.rpg.command;

import com.rpg.model.Character;

public class DefendCommand implements Command {
    private Character defender;
    private int originalHealth;

    public DefendCommand(Character defender) {
        this.defender = defender;
    }

    @Override
    public void execute() {
        originalHealth = defender.getHealth();
        int healthBonus = 20 + defender.getAgility() / 2;
        defender.setHealth(defender.getHealth() + healthBonus);
        System.out.println(defender.getName() + " se met en position défensive et gagne " + 
                         healthBonus + " PV temporaires!");
        System.out.println(defender.getName() + " a maintenant " + defender.getHealth() + " PV");
    }

    @Override
    public String toString() {
        return defender.getName() + " défend";
    }
} 