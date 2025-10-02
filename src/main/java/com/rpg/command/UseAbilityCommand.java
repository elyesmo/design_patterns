package com.rpg.command;

import com.rpg.model.Character;
import java.util.List;

public class UseAbilityCommand implements Command {
    private Character user;
    private Character target;
    private String ability;

    public UseAbilityCommand(Character user, Character target, String ability) {
        this.user = user;
        this.target = target;
        this.ability = ability;
    }

    @Override
    public void execute() {
        List<String> abilities = user.getAbilities();
        
        if (!abilities.contains(ability)) {
            System.out.println(user.getName() + " ne possède pas la capacité: " + ability);
            return;
        }

        System.out.println(user.getName() + " utilise " + ability + " sur " + target.getName() + "!");
        
        switch (ability) {
            case "Invisibilite":
                System.out.println(user.getName() + " devient invisible et esquive les attaques!");
                user.setAgility(user.getAgility() + 3);
                break;
            case "Telepathie":
                int mentalDamage = user.getIntelligence();
                target.setHealth(Math.max(0, target.getHealth() - mentalDamage));
                System.out.println("Attaque mentale! " + target.getName() + " perd " + mentalDamage + " PV");
                break;
            case "Super Force":
                int damage = user.getStrength() * 2;
                target.setHealth(Math.max(0, target.getHealth() - damage));
                System.out.println("Coup surpuissant! " + target.getName() + " perd " + damage + " PV");
                break;
            case "Regeneration":
                int heal = 30;
                user.setHealth(user.getHealth() + heal);
                System.out.println(user.getName() + " se régénère et gagne " + heal + " PV");
                break;
            case "Pouvoir du Feu":
                int fireDamage = user.getStrength() + user.getIntelligence();
                target.setHealth(Math.max(0, target.getHealth() - fireDamage));
                System.out.println("Boule de feu! " + target.getName() + " perd " + fireDamage + " PV");
                break;
        }
        
        System.out.println(target.getName() + " a maintenant " + target.getHealth() + " PV");
    }

    @Override
    public String toString() {
        return user.getName() + " utilise " + ability + " sur " + target.getName();
    }
} 