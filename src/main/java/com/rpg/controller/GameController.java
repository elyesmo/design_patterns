package com.rpg.controller;

import com.rpg.builder.CharacterBuilder;
import com.rpg.command.AttackCommand;
import com.rpg.dao.FileCharacterDAO;
import com.rpg.decorator.AbilityDecorator;
import com.rpg.model.Character;
import com.rpg.model.Team;
import com.rpg.validator.*;
import com.rpg.view.ConsoleView;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private ConsoleView view;
    private List<Character> characters;
    private List<Team> teams;
    private FileCharacterDAO dao;
    private CharacterValidator validator;

    public GameController() {
        this.view = new ConsoleView();
        this.characters = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.dao = new FileCharacterDAO();
        setupValidator();
    }

    private void setupValidator() {
        validator = new NameValidator();
        StatsValidator statsValidator = new StatsValidator();
        AbilitiesValidator abilitiesValidator = new AbilitiesValidator();

        validator.setNext(statsValidator);
        statsValidator.setNext(abilitiesValidator);
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                view.showMenu();
                String choice = view.getUserInput();

                switch (choice) {
                    case "1": createCharacter(); break;
                    case "2": addAbilities(); break;
                    case "3": showCharacters(); break;
                    case "4": createTeam(); break;
                    case "5": combat(); break;
                    case "6": saveCharacters(); break;
                    case "7": loadCharacters(); break;
                    case "0": running = false; break;
                    default: view.showMessage("Choix invalide. Entrez 0-7");
                }
            } catch (Exception e) {
                view.showMessage("Erreur, retour au menu...");
            }
        }
        view.showMessage("Au revoir!");
    }

    private void createCharacter() {
        String name = view.askInput("Nom du personnage");
        int strength = view.askIntInput("Force (1-20)");
        int agility = view.askIntInput("Agilite (1-20)");
        int intelligence = view.askIntInput("Intelligence (1-20)");

        Character character = new CharacterBuilder()
            .setName(name)
            .setStrength(strength)
            .setAgility(agility)
            .setIntelligence(intelligence)
            .build();

        if (validator.validate(character)) {
            characters.add(character);
            view.showMessage("Personnage cree: " + character);
        } else {
            view.showMessage("Personnage invalide");
        }
    }

    private void addAbilities() {
        if (characters.isEmpty()) {
            view.showMessage("Aucun personnage disponible");
            return;
        }

        showCharacters();
        int index = view.askIntInput("Index du personnage (0-" + (characters.size()-1) + ")");

        if (index >= 0 && index < characters.size()) {
            Character character = characters.get(index);

            view.showMessage("Capacites disponibles:");
            view.showMessage("1. Invisibilite  2. Telepathie  3. Super Force  4. Regeneration  5. Pouvoir du Feu");
            int ability = view.askIntInput("Choix capacite (1-5)");

            switch (ability) {
                case 1: AbilityDecorator.addInvisibility(character); break;
                case 2: AbilityDecorator.addTelepathy(character); break;
                case 3: AbilityDecorator.addSuperStrength(character); break;
                case 4: AbilityDecorator.addRegeneration(character); break;
                case 5: AbilityDecorator.addFirePower(character); break;
                default: view.showMessage("Capacite invalide"); return;
            }

            if (validator.validate(character)) {
                view.showMessage("Capacite ajoutee: " + character);
            } else {
                character.getAbilities().remove(character.getAbilities().size() - 1);
                view.showMessage("Trop de capacites");
            }
        }
    }

    private void showCharacters() {
        if (characters.isEmpty()) {
            view.showMessage("Aucun personnage cree");
            return;
        }

        view.showMessage("\n=== PERSONNAGES ===");
        for (int i = 0; i < characters.size(); i++) {
            view.showMessage(i + ". " + characters.get(i));
        }
    }

    private void createTeam() {
        if (characters.size() < 2) {
            view.showMessage("Il faut au moins 2 personnages");
            return;
        }

        String teamName = view.askInput("Nom de l'equipe");
        Team team = new Team(teamName);

        showCharacters();
        view.showMessage("Entrez les indices des personnages (separes par des espaces)");
        String indices = view.getUserInput();

        for (String indexStr : indices.split(" ")) {
            try {
                int index = Integer.parseInt(indexStr.trim());
                if (index >= 0 && index < characters.size()) {
                    team.addMember(characters.get(index));
                }
            } catch (NumberFormatException e) {
            }
        }

        teams.add(team);
        view.showMessage("Equipe creee: " + team);
    }

    private void combat() {
        if (characters.size() < 2) {
            view.showMessage("Il faut au moins 2 personnages");
            return;
        }

        showCharacters();
        int attacker = view.askIntInput("Index attaquant (0-" + (characters.size()-1) + ")");
        int target = view.askIntInput("Index defenseur (0-" + (characters.size()-1) + ")");

        if (attacker >= 0 && attacker < characters.size() && 
            target >= 0 && target < characters.size() && attacker != target) {

            AttackCommand attack = new AttackCommand(characters.get(attacker), characters.get(target));
            attack.execute();

            Character winner = characters.get(attacker).getPowerLevel() > characters.get(target).getPowerLevel() 
                             ? characters.get(attacker) : characters.get(target);
            view.showMessage("Le plus puissant est: " + winner.getName());
        } else {
            view.showMessage("Indices invalides");
        }
    }

    private void saveCharacters() {
        for (Character character : characters) {
            dao.save(character);
        }
        view.showMessage("Personnages sauvegardes dans characters.txt");
    }

    private void loadCharacters() {
        List<Character> loaded = dao.loadAll();
        characters.addAll(loaded);
        view.showMessage("Charges " + loaded.size() + " personnages");
    }
}
