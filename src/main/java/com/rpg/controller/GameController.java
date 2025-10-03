package com.rpg.controller;

import com.rpg.builder.CharacterBuilder;
import com.rpg.command.*;
import com.rpg.combat.*;
import com.rpg.composite.*;
import com.rpg.dao.FileCharacterDAO;
import com.rpg.decorator.AbilityDecorator;
import com.rpg.model.Character;
import com.rpg.observer.CombatLogger;
import com.rpg.singleton.GameSettings;
import com.rpg.strategy.*;
import com.rpg.util.DisplayUtil;
import com.rpg.validator.*;
import com.rpg.view.ConsoleView;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation") // Using legacy AbilityDecorator for backward compatibility
public class GameController {
    private ConsoleView view;
    private List<Character> characters;
    private List<TeamComposite> teams;
    private List<TeamComposite> armies;
    private FileCharacterDAO dao;
    private CharacterValidator validator;
    private CommandHistory commandHistory;
    private CombatLogger combatLogger;
    private TeamBattle teamBattle;

    public GameController() {
        this.view = new ConsoleView();
        this.characters = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.armies = new ArrayList<>();
        this.dao = new FileCharacterDAO();
        this.commandHistory = new CommandHistory();
        this.combatLogger = new CombatLogger();
        this.teamBattle = new TeamBattle(combatLogger);
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
        view.showMessage("╔══════════════════════════════════════════════════════╗");
        view.showMessage("║  GÉNÉRATEUR DE PERSONNAGES RPG - VERSION ÉTENDUE    ║");
        view.showMessage("║  Patterns: Builder, Singleton, DAO, Decorator,      ║");
        view.showMessage("║  Chain of Responsibility, Command, Composite,        ║");
        view.showMessage("║  Observer, MVC                                       ║");
        view.showMessage("╚══════════════════════════════════════════════════════╝");
        
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
                    case "5": createArmy(); break;
                    case "6": displayTeamsAndArmies(); break;
                    case "7": combat(); break;
                    case "8": advancedCombat(); break;
                    case "9": teamBattleMenu(); break;
                    case "10": commandHistory.displayHistory(); break;
                    case "11": commandHistory.replay(); break;
                    case "12": combatLogger.displayLogs(); break;
                    case "13": saveCharacters(); break;
                    case "14": loadCharacters(); break;
                    case "15": configureSettings(); break;
                    case "0": running = false; break;
                    default: view.showMessage(DisplayUtil.error("Choix invalide. Entrez un nombre entre 0 et 15"));
                }
            } catch (Exception e) {
                view.showMessage("❌ Erreur: " + e.getMessage());
                view.showMessage("Retour au menu...");
            }
        }
        view.showMessage("\n👋 Au revoir et bonne aventure!");
    }

    private void createCharacter() {
        DisplayUtil.printSectionTitle("CRÉATION D'UN PERSONNAGE");
        System.out.println(DisplayUtil.createBorder("🎭 Nouveau Héros 🎭", 70));
        
        String name = view.askInput("Nom du personnage");
        
        GameSettings settings = GameSettings.getInstance();
        System.out.println(DisplayUtil.info("Points de stats maximum: " + settings.getMaxStatPoints()));
        System.out.println();
        
        int strength = view.askIntInput("⚔  Force (1-20)");
        int agility = view.askIntInput("🏃 Agilité (1-20)");
        int intelligence = view.askIntInput("🧠 Intelligence (1-20)");

        Character character = new CharacterBuilder()
            .setName(name)
            .setStrength(strength)
            .setAgility(agility)
            .setIntelligence(intelligence)
            .build();

        ValidationResult validationResult = validator.validateWithErrors(character);
        if (validationResult.isValid()) {
            characters.add(character);
            System.out.println("\n" + DisplayUtil.closeBorder(70));
            System.out.println(DisplayUtil.success("Personnage créé avec succès!"));
            System.out.println("\n📋 Détails:");
            System.out.println("  • Nom: " + DisplayUtil.colorize(character.getName(), DisplayUtil.BOLD));
            System.out.println("  • Niveau: " + character.getLevel());
            System.out.println("  • " + DisplayUtil.createHealthBar(character.getHealth(), character.getMaxHealth(), 20));
            System.out.println(String.format("  • Stats: ⚔ %d | 🏃 %d | 🧠 %d", 
                character.getStrength(), character.getAgility(), character.getIntelligence()));
            System.out.println("  • Puissance: " + DisplayUtil.colorize(String.valueOf(character.getPowerLevel()), 
                DisplayUtil.GREEN));
        } else {
            System.out.println("\n" + DisplayUtil.closeBorder(70));
            view.showMessage(DisplayUtil.error("Personnage invalide!"));
            for (String error : validationResult.getErrors()) {
                view.showMessage(DisplayUtil.warning("  • " + error));
            }
        }
    }

    private void addAbilities() {
        if (characters.isEmpty()) {
            view.showMessage(DisplayUtil.error("Aucun personnage disponible"));
            return;
        }

        DisplayUtil.printSectionTitle("AJOUT DE CAPACITÉS SPÉCIALES");
        showCharacters();
        int index = view.askIntInput("\nChoisir personnage (0-" + (characters.size()-1) + ")");

        if (index >= 0 && index < characters.size()) {
            Character character = characters.get(index);

            System.out.println("\n" + DisplayUtil.createBorder("📚 Capacités Disponibles 📚", 70));
            System.out.println(DisplayUtil.colorize("  1. 👻 Invisibilité", DisplayUtil.CYAN) + " - +5 Agilité");
            System.out.println("     Permet d'esquiver plus facilement");
            System.out.println(DisplayUtil.colorize("\n  2. 🧠 Télépathie", DisplayUtil.PURPLE) + " - +5 Intelligence");
            System.out.println("     Lecture de pensées et attaques mentales");
            System.out.println(DisplayUtil.colorize("\n  3. 💪 Super Force", DisplayUtil.YELLOW) + " - +8 Force");
            System.out.println("     Coups dévastateurs");
            System.out.println(DisplayUtil.colorize("\n  4. 💚 Régénération", DisplayUtil.GREEN) + " - +50 PV");
            System.out.println("     Guérison rapide et résilience");
            System.out.println(DisplayUtil.colorize("\n  5. 🔥 Pouvoir du Feu", DisplayUtil.RED) + " - +3 Force, +3 Intelligence");
            System.out.println("     Maîtrise des flammes");
            System.out.println("\n" + DisplayUtil.closeBorder(70));
            
            int ability = view.askIntInput("Choix capacité (1-5)");

            String abilityName = "";
            switch (ability) {
                case 1: 
                    AbilityDecorator.addInvisibility(character);
                    abilityName = "Invisibilité";
                    break;
                case 2: 
                    AbilityDecorator.addTelepathy(character);
                    abilityName = "Télépathie";
                    break;
                case 3: 
                    AbilityDecorator.addSuperStrength(character);
                    abilityName = "Super Force";
                    break;
                case 4: 
                    AbilityDecorator.addRegeneration(character);
                    abilityName = "Régénération";
                    break;
                case 5: 
                    AbilityDecorator.addFirePower(character);
                    abilityName = "Pouvoir du Feu";
                    break;
                default: 
                    view.showMessage(DisplayUtil.error("Capacité invalide")); 
                    return;
            }

            ValidationResult abilityValidation = validator.validateWithErrors(character);
            if (abilityValidation.isValid()) {
                System.out.println(DisplayUtil.success("Capacité '" + abilityName + "' ajoutée à " + character.getName() + "!"));
                System.out.println("\n📊 Stats mises à jour:");
                System.out.println(String.format("  • ⚔ Force: %d | 🏃 Agilité: %d | 🧠 Intelligence: %d",
                    character.getStrength(), character.getAgility(), character.getIntelligence()));
                System.out.println("  • " + DisplayUtil.createHealthBar(character.getHealth(), character.getMaxHealth(), 20));
                System.out.println("  • Puissance totale: " + DisplayUtil.colorize(String.valueOf(character.getPowerLevel()), 
                    DisplayUtil.GREEN));
                System.out.println("  • Capacités: " + String.join(", ", character.getAbilities()));
            } else {
                character.getAbilities().remove(character.getAbilities().size() - 1);
                view.showMessage(DisplayUtil.error("Erreur de validation:"));
                for (String error : abilityValidation.getErrors()) {
                    view.showMessage(DisplayUtil.warning("  • " + error));
                }
            }
        }
    }

    private void showCharacters() {
        if (characters.isEmpty()) {
            view.showMessage(DisplayUtil.info("Aucun personnage créé"));
            return;
        }

        DisplayUtil.printSectionTitle("LISTE DES PERSONNAGES");
        System.out.println(DisplayUtil.createBorder("👥 Roster de Personnages 👥", 70));
        
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            
            // Ligne de séparation entre personnages
            if (i > 0) {
                System.out.println("  " + "─".repeat(66));
            }
            
            // Nom et niveau
            System.out.println(String.format("  %s%d. %s [Niveau %d]%s", 
                DisplayUtil.BOLD, i, c.getName(), c.getLevel(), DisplayUtil.RESET));
            
            // Barre de vie
            System.out.println("  ❤  " + DisplayUtil.createHealthBar(c.getHealth(), c.getMaxHealth(), 20));
            
            // Stats sur une ligne
            System.out.println(String.format("  📊 Stats: %s⚔ %d%s | %s🏃 %d%s | %s🧠 %d%s | Puissance: %s%d%s",
                DisplayUtil.YELLOW, c.getStrength(), DisplayUtil.RESET,
                DisplayUtil.CYAN, c.getAgility(), DisplayUtil.RESET,
                DisplayUtil.PURPLE, c.getIntelligence(), DisplayUtil.RESET,
                DisplayUtil.GREEN, c.getPowerLevel(), DisplayUtil.RESET));
            
            // Capacités
            if (!c.getAbilities().isEmpty()) {
                System.out.println("  📚 Capacités: " + DisplayUtil.colorize(
                    String.join(", ", c.getAbilities()), DisplayUtil.BLUE));
            } else {
                System.out.println("  📚 Capacités: " + DisplayUtil.colorize("Aucune", DisplayUtil.WHITE));
            }
            
            // Expérience
            if (c.getLevel() > 1 || c.getExperience() > 0) {
                System.out.println(String.format("  ⭐ XP: %d/100 (Niveau suivant: %d XP)", 
                    c.getExperience(), 100 - c.getExperience()));
            }
        }
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        System.out.println(DisplayUtil.info("Total: " + characters.size() + " personnage(s)"));
    }

    private void createTeam() {
        if (characters.size() < 2) {
            view.showMessage("❌ Il faut au moins 2 personnages pour créer une équipe");
            return;
        }

        view.showMessage("\n=== CRÉATION D'UNE ÉQUIPE ===");
        String teamName = view.askInput("Nom de l'équipe");
        TeamComposite team = new TeamComposite(teamName, "TEAM");
        
        // Ajouter l'observateur
        team.addObserver(combatLogger);

        showCharacters();
        view.showMessage("Entrez les indices des personnages (séparés par des espaces)");
        view.showMessage("Maximum " + GameSettings.getInstance().getMaxCharactersPerTeam() + " personnages");
        String indices = view.getUserInput();

        int added = 0;
        for (String indexStr : indices.split(" ")) {
            try {
                int index = Integer.parseInt(indexStr.trim());
                if (index >= 0 && index < characters.size()) {
                    CharacterLeaf charLeaf = new CharacterLeaf(characters.get(index));
                    team.add(charLeaf);
                    added++;
                }
            } catch (NumberFormatException e) {
                // Ignorer les entrées invalides
            } catch (IllegalStateException e) {
                view.showMessage("⚠ " + e.getMessage());
                break;
            }
        }

        if (added > 0) {
            teams.add(team);
            view.showMessage("✅ Équipe créée: " + team);
        } else {
            view.showMessage("❌ Aucun personnage ajouté");
        }
    }

    private void createArmy() {
        if (teams.size() < 2) {
            view.showMessage("❌ Il faut au moins 2 équipes pour créer une armée");
            return;
        }

        view.showMessage("\n=== CRÉATION D'UNE ARMÉE ===");
        String armyName = view.askInput("Nom de l'armée");
        TeamComposite army = new TeamComposite(armyName, "ARMY");
        
        // Ajouter l'observateur
        army.addObserver(combatLogger);

        displayTeams();
        view.showMessage("Entrez les indices des équipes (séparés par des espaces)");
        view.showMessage("Maximum " + GameSettings.getInstance().getMaxTeamsPerArmy() + " équipes");
        String indices = view.getUserInput();

        int added = 0;
        for (String indexStr : indices.split(" ")) {
            try {
                int index = Integer.parseInt(indexStr.trim());
                if (index >= 0 && index < teams.size()) {
                    army.add(teams.get(index));
                    added++;
                }
            } catch (NumberFormatException e) {
                // Ignorer les entrées invalides
            } catch (IllegalStateException e) {
                view.showMessage("⚠ " + e.getMessage());
                break;
            }
        }

        if (added > 0) {
            armies.add(army);
            view.showMessage("✅ Armée créée: " + army);
        } else {
            view.showMessage("❌ Aucune équipe ajoutée");
        }
    }

    private void displayTeams() {
        if (teams.isEmpty()) {
            view.showMessage("📋 Aucune équipe créée");
            return;
        }

        view.showMessage("\n═══ LISTE DES ÉQUIPES ═══");
        for (int i = 0; i < teams.size(); i++) {
            view.showMessage(i + ". " + teams.get(i));
        }
    }

    private void displayTeamsAndArmies() {
        view.showMessage("\n╔═══════════════════════════════════════════════════════╗");
        view.showMessage("║           HIÉRARCHIE DES ÉQUIPES ET ARMÉES            ║");
        view.showMessage("╚═══════════════════════════════════════════════════════╝");
        
        if (teams.isEmpty() && armies.isEmpty()) {
            view.showMessage("📋 Aucune équipe ou armée créée");
            return;
        }

        if (!teams.isEmpty()) {
            view.showMessage("\n👥 ÉQUIPES:");
            for (TeamComposite team : teams) {
                team.display("  ");
            }
        }

        if (!armies.isEmpty()) {
            view.showMessage("\n🛡 ARMÉES:");
            for (TeamComposite army : armies) {
                army.display("  ");
            }
        }
    }

    private void combat() {
        if (characters.size() < 2) {
            view.showMessage(DisplayUtil.error("Il faut au moins 2 personnages"));
            return;
        }

        DisplayUtil.printSectionTitle("COMBAT SIMPLE");
        showCharacters();
        int attackerIdx = view.askIntInput("Index attaquant (0-" + (characters.size()-1) + ")");
        int targetIdx = view.askIntInput("Index défenseur (0-" + (characters.size()-1) + ")");

        if (attackerIdx < 0 || attackerIdx >= characters.size() || 
            targetIdx < 0 || targetIdx >= characters.size() || attackerIdx == targetIdx) {
            view.showMessage(DisplayUtil.error("Indices invalides"));
            return;
        }

        Character attacker = characters.get(attackerIdx);
        Character defender = characters.get(targetIdx);

        // Sauvegarder les PV initiaux
        int attackerInitialHP = attacker.getHealth();
        int defenderInitialHP = defender.getHealth();

        // Affichage de l'introduction
        System.out.println(DisplayUtil.createBorder("⚔ DUEL ⚔", 70));
        System.out.println(DisplayUtil.colorize("  " + attacker.getName() + " [Niv." + attacker.getLevel() + "]", 
                                                DisplayUtil.CYAN + DisplayUtil.BOLD));
        System.out.println("    Puissance: " + attacker.getPowerLevel());
        System.out.println("    " + DisplayUtil.createHealthBar(attacker.getHealth(), attacker.getMaxHealth(), 20));
        
        System.out.println(DisplayUtil.colorize("\n          ⚔ CONTRE ⚔\n", DisplayUtil.YELLOW));
        
        System.out.println(DisplayUtil.colorize("  " + defender.getName() + " [Niv." + defender.getLevel() + "]", 
                                                DisplayUtil.PURPLE + DisplayUtil.BOLD));
        System.out.println("    Puissance: " + defender.getPowerLevel());
        System.out.println("    " + DisplayUtil.createHealthBar(defender.getHealth(), defender.getMaxHealth(), 20));
        System.out.println("\n" + DisplayUtil.closeBorder(70));

        combatLogger.logCombatEvent("Combat entre " + attacker.getName() + " et " + defender.getName());

        // Phase 1: Attaquant frappe
        DisplayUtil.printSeparator();
        System.out.println(DisplayUtil.colorize("\n▶ " + attacker.getName() + " attaque!", DisplayUtil.CYAN + DisplayUtil.BOLD));
        Command attackCmd = new AttackCommand(attacker, defender);
        attackCmd.execute();
        commandHistory.addCommand(attackCmd);

        System.out.println("\n  État après l'attaque:");
        System.out.println("    " + defender.getName() + ": " + 
                         DisplayUtil.createHealthBar(defender.getHealth(), defender.getMaxHealth(), 20));

        // Pause visuelle
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Phase 2: Contre-attaque si encore en vie
        if (defender.getHealth() > 0) {
            DisplayUtil.printSeparator();
            System.out.println(DisplayUtil.colorize("\n▶ " + defender.getName() + " contre-attaque!", 
                                                   DisplayUtil.PURPLE + DisplayUtil.BOLD));
            Command counterAttackCmd = new AttackCommand(defender, attacker);
            counterAttackCmd.execute();
            commandHistory.addCommand(counterAttackCmd);

            System.out.println("\n  État après la contre-attaque:");
            System.out.println("    " + attacker.getName() + ": " + 
                             DisplayUtil.createHealthBar(attacker.getHealth(), attacker.getMaxHealth(), 20));

            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }

        // Résultats finaux
        DisplayUtil.printSeparator();
        System.out.println(DisplayUtil.createBorder("📊 RÉSULTATS DU COMBAT 📊", 70));
        
        // Tableau comparatif
        System.out.println(String.format("  %-20s  %-25s  %-25s", "", attacker.getName(), defender.getName()));
        System.out.println("  " + "─".repeat(68));
        
        System.out.println(String.format("  %-20s  %d → %d (%+d)", "Points de Vie", 
            attackerInitialHP, attacker.getHealth(), attacker.getHealth() - attackerInitialHP));
        System.out.println(String.format("  %-20s  %d → %d (%+d)", "", 
            defenderInitialHP, defender.getHealth(), defender.getHealth() - defenderInitialHP));
        
        System.out.println("\n  " + "─".repeat(68));
        
        // Déterminer le vainqueur
        Character winner = null;
        if (attacker.getHealth() > defender.getHealth()) {
            winner = attacker;
            System.out.println(DisplayUtil.colorize("\n  🏆 VAINQUEUR: " + attacker.getName(), 
                                                   DisplayUtil.GREEN + DisplayUtil.BOLD));
            System.out.println("    Plus de PV restants (" + attacker.getHealth() + " vs " + defender.getHealth() + ")");
            attacker.addExperience(30);
            System.out.println(DisplayUtil.info("    +" + 30 + " XP (Total: " + attacker.getExperience() + "/100)"));
        } else if (defender.getHealth() > attacker.getHealth()) {
            winner = defender;
            System.out.println(DisplayUtil.colorize("\n  🏆 VAINQUEUR: " + defender.getName(), 
                                                   DisplayUtil.GREEN + DisplayUtil.BOLD));
            System.out.println("    Plus de PV restants (" + defender.getHealth() + " vs " + attacker.getHealth() + ")");
            defender.addExperience(30);
            System.out.println(DisplayUtil.info("    +" + 30 + " XP (Total: " + defender.getExperience() + "/100)"));
        } else {
            System.out.println(DisplayUtil.colorize("\n  ⚖ MATCH NUL", DisplayUtil.YELLOW + DisplayUtil.BOLD));
            System.out.println("    Les deux combattants ont le même nombre de PV");
            attacker.addExperience(15);
            defender.addExperience(15);
        }

        System.out.println("\n" + DisplayUtil.closeBorder(70));
        
        if (winner != null) {
            combatLogger.logCombatEvent(winner.getName() + " remporte le duel!");
        }
    }

    private void advancedCombat() {
        if (characters.size() < 2) {
            view.showMessage(DisplayUtil.error("Il faut au moins 2 personnages"));
            return;
        }

        DisplayUtil.printSectionTitle("COMBAT AVANCÉ - TOUR PAR TOUR");
        showCharacters();
        int char1Index = view.askIntInput("Personnage 1 (0-" + (characters.size()-1) + ")");
        int char2Index = view.askIntInput("Personnage 2 (0-" + (characters.size()-1) + ")");

        if (char1Index < 0 || char1Index >= characters.size() || 
            char2Index < 0 || char2Index >= characters.size() || 
            char1Index == char2Index) {
            view.showMessage(DisplayUtil.error("Indices invalides"));
            return;
        }

        Character char1 = characters.get(char1Index);
        Character char2 = characters.get(char2Index);

        // Sauvegarder stats initiales
        int char1InitialHP = char1.getHealth();
        int char2InitialHP = char2.getHealth();
        int char1Actions = 0;
        int char2Actions = 0;
        long startTime = System.currentTimeMillis();

        // Affichage introduction
        System.out.println(DisplayUtil.createBorder("⚔ COMBAT AVANCÉ ⚔", 70));
        System.out.println(DisplayUtil.colorize("  " + char1.getName() + " [Niv." + char1.getLevel() + "]", 
                                                DisplayUtil.CYAN + DisplayUtil.BOLD));
        System.out.println("    ⚔ Force: " + char1.getStrength() + 
                         " | 🏃 Agilité: " + char1.getAgility() + 
                         " | 🧠 Intelligence: " + char1.getIntelligence());
        System.out.println("    " + DisplayUtil.createHealthBar(char1.getHealth(), char1.getMaxHealth(), 20));
        if (!char1.getAbilities().isEmpty()) {
            System.out.println("    📚 Capacités: " + String.join(", ", char1.getAbilities()));
        }
        
        System.out.println(DisplayUtil.colorize("\n          ⚔ CONTRE ⚔\n", DisplayUtil.YELLOW));
        
        System.out.println(DisplayUtil.colorize("  " + char2.getName() + " [Niv." + char2.getLevel() + "]", 
                                                DisplayUtil.PURPLE + DisplayUtil.BOLD));
        System.out.println("    ⚔ Force: " + char2.getStrength() + 
                         " | 🏃 Agilité: " + char2.getAgility() + 
                         " | 🧠 Intelligence: " + char2.getIntelligence());
        System.out.println("    " + DisplayUtil.createHealthBar(char2.getHealth(), char2.getMaxHealth(), 20));
        if (!char2.getAbilities().isEmpty()) {
            System.out.println("    📚 Capacités: " + String.join(", ", char2.getAbilities()));
        }
        System.out.println("\n" + DisplayUtil.closeBorder(70));

        combatLogger.logCombatEvent("Début du combat avancé entre " + char1.getName() + 
                                   " et " + char2.getName());

        boolean fighting = true;
        int round = 1;

        while (fighting && char1.getHealth() > 0 && char2.getHealth() > 0) {
            DisplayUtil.printSeparator();
            System.out.println(DisplayUtil.colorize("\n═══ ROUND " + round + " ═══", 
                                                   DisplayUtil.BOLD + DisplayUtil.YELLOW));
            DisplayUtil.printSeparator();
            
            // Affichage de l'état actuel
            System.out.println("\n📊 État des combattants:");
            System.out.println("  • " + char1.getName() + ": " + 
                             DisplayUtil.createHealthBar(char1.getHealth(), char1.getMaxHealth(), 20));
            System.out.println("  • " + char2.getName() + ": " + 
                             DisplayUtil.createHealthBar(char2.getHealth(), char2.getMaxHealth(), 20));

            // Tour du personnage 1
            System.out.println(DisplayUtil.colorize("\n▶ Tour de " + char1.getName(), 
                                                   DisplayUtil.CYAN + DisplayUtil.BOLD));
            Command cmd1 = chooseAction(char1, char2);
            if (cmd1 != null) {
                cmd1.execute();
                commandHistory.addCommand(cmd1);
                char1Actions++;
            }

            // Pause visuelle
            try { Thread.sleep(800); } catch (InterruptedException e) {}

            if (char2.getHealth() <= 0) {
                displayCombatVictory(char1, char2, round, char1Actions, char2Actions, 
                                    char1InitialHP, char2InitialHP, startTime);
                break;
            }

            // Tour du personnage 2
            System.out.println(DisplayUtil.colorize("\n▶ Tour de " + char2.getName(), 
                                                   DisplayUtil.PURPLE + DisplayUtil.BOLD));
            Command cmd2 = chooseAction(char2, char1);
            if (cmd2 != null) {
                cmd2.execute();
                commandHistory.addCommand(cmd2);
                char2Actions++;
            }

            try { Thread.sleep(800); } catch (InterruptedException e) {}

            if (char1.getHealth() <= 0) {
                displayCombatVictory(char2, char1, round, char2Actions, char1Actions, 
                                    char2InitialHP, char1InitialHP, startTime);
                break;
            }

            // Demander si on continue
            System.out.print(DisplayUtil.info("\nContinuer le combat? (o/n): "));
            String choice = view.getUserInput();
            if (choice.equalsIgnoreCase("n")) {
                fighting = false;
                displayCombatDraw(char1, char2, round, char1Actions, char2Actions, startTime);
            }

            round++;
        }
    }

    private void displayCombatVictory(Character winner, Character loser, int rounds, 
                                     int winnerActions, int loserActions,
                                     int winnerInitialHP, int loserInitialHP, long startTime) {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        
        System.out.println("\n" + DisplayUtil.createBorder("🏆 VICTOIRE 🏆", 70));
        System.out.println(DisplayUtil.colorize("  " + winner.getName() + " remporte le combat!", 
                                                DisplayUtil.GREEN + DisplayUtil.BOLD));
        
        System.out.println("\n  📊 Statistiques du combat:");
        System.out.println("    • Durée: " + duration + " secondes");
        System.out.println("    • Rounds: " + rounds);
        System.out.println("    • Actions totales: " + (winnerActions + loserActions));
        
        System.out.println("\n  👤 Vainqueur - " + winner.getName() + ":");
        System.out.println("    " + DisplayUtil.createHealthBar(winner.getHealth(), winner.getMaxHealth(), 20));
        System.out.println("    • PV: " + winnerInitialHP + " → " + winner.getHealth() + 
                         " (" + (winner.getHealth() - winnerInitialHP) + ")");
        System.out.println("    • Actions: " + winnerActions);
        
        System.out.println("\n  💀 Vaincu - " + loser.getName() + ":");
        System.out.println("    " + DisplayUtil.createHealthBar(loser.getHealth(), loser.getMaxHealth(), 20));
        System.out.println("    • PV: " + loserInitialHP + " → " + loser.getHealth() + 
                         " (" + (loser.getHealth() - loserInitialHP) + ")");
        System.out.println("    • Actions: " + loserActions);
        
        // Expérience
        int expGain = 50;
        winner.addExperience(expGain);
        System.out.println("\n  " + DisplayUtil.success(winner.getName() + " gagne " + expGain + 
                         " XP (Total: " + winner.getExperience() + "/100)"));
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        
        combatLogger.logCombatEvent(winner.getName() + " a vaincu " + loser.getName() + 
                                   " après " + rounds + " rounds");
    }

    private void displayCombatDraw(Character char1, Character char2, int rounds, 
                                  int actions1, int actions2, long startTime) {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        
        System.out.println("\n" + DisplayUtil.createBorder("⚖ COMBAT INTERROMPU ⚖", 70));
        System.out.println(DisplayUtil.colorize("  Le combat s'arrête avant qu'un vainqueur ne soit désigné", 
                                                DisplayUtil.YELLOW + DisplayUtil.BOLD));
        
        System.out.println("\n  📊 Statistiques:");
        System.out.println("    • Durée: " + duration + " secondes");
        System.out.println("    • Rounds: " + rounds);
        System.out.println("    • Actions totales: " + (actions1 + actions2));
        
        System.out.println("\n  État final:");
        System.out.println("    • " + char1.getName() + ": " + 
                         DisplayUtil.createHealthBar(char1.getHealth(), char1.getMaxHealth(), 20));
        System.out.println("    • " + char2.getName() + ": " + 
                         DisplayUtil.createHealthBar(char2.getHealth(), char2.getMaxHealth(), 20));
        
        // Expérience réduite
        char1.addExperience(20);
        char2.addExperience(20);
        System.out.println("\n  " + DisplayUtil.info("Les deux combattants gagnent 20 XP"));
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        
        combatLogger.logCombatEvent("Combat interrompu après " + rounds + " rounds");
    }

    private Command chooseAction(Character actor, Character target) {
        view.showMessage("\n" + actor.getName() + " - Actions:");
        view.showMessage("  1. Attaquer");
        view.showMessage("  2. Défendre");
        if (!actor.getAbilities().isEmpty()) {
            view.showMessage("  3. Utiliser une capacité");
        }
        
        int action = view.askIntInput("Choix");
        
        switch (action) {
            case 1:
                return new AttackCommand(actor, target);
            case 2:
                return new DefendCommand(actor);
            case 3:
                if (!actor.getAbilities().isEmpty()) {
                    view.showMessage("Capacités de " + actor.getName() + ":");
                    List<String> abilities = actor.getAbilities();
                    for (int i = 0; i < abilities.size(); i++) {
                        view.showMessage("  " + (i+1) + ". " + abilities.get(i));
                    }
                    int abilityIndex = view.askIntInput("Choix capacité") - 1;
                    if (abilityIndex >= 0 && abilityIndex < abilities.size()) {
                        return new UseAbilityCommand(actor, target, abilities.get(abilityIndex));
                    }
                }
                break;
        }
        return null;
    }

    private void saveCharacters() {
        for (Character character : characters) {
            dao.save(character);
        }
        view.showMessage("✅ " + characters.size() + " personnages sauvegardés dans characters.txt");
    }

    private void loadCharacters() {
        List<Character> loaded = dao.loadAll();
        characters.addAll(loaded);
        view.showMessage("✅ Chargés " + loaded.size() + " personnages depuis characters.txt");
    }

    private void configureSettings() {
        GameSettings settings = GameSettings.getInstance();
        view.showMessage("\n=== PARAMÈTRES DU JEU ===");
        view.showMessage("1. Points de stats maximum: " + settings.getMaxStatPoints());
        view.showMessage("2. Personnages max par équipe: " + settings.getMaxCharactersPerTeam());
        view.showMessage("3. Capacités max par personnage: " + settings.getMaxAbilities());
        view.showMessage("4. Équipes max par armée: " + settings.getMaxTeamsPerArmy());
        view.showMessage("0. Retour");

        int choice = view.askIntInput("Modifier quel paramètre?");
        
        switch (choice) {
            case 1:
                int maxStats = view.askIntInput("Nouvelle valeur pour points de stats max");
                settings.setMaxStatPoints(maxStats);
                view.showMessage("✅ Paramètre modifié");
                break;
            case 2:
                int maxChars = view.askIntInput("Nouvelle valeur pour personnages max par équipe");
                settings.setMaxCharactersPerTeam(maxChars);
                view.showMessage("✅ Paramètre modifié");
                break;
            case 3:
                int maxAbilities = view.askIntInput("Nouvelle valeur pour capacités max");
                settings.setMaxAbilities(maxAbilities);
                view.showMessage("✅ Paramètre modifié");
                break;
            case 4:
                int maxTeams = view.askIntInput("Nouvelle valeur pour équipes max par armée");
                settings.setMaxTeamsPerArmy(maxTeams);
                view.showMessage("✅ Paramètre modifié");
                break;
        }
    }

    private void teamBattleMenu() {
        if (teams.size() < 2 && armies.size() == 0) {
            view.showMessage(DisplayUtil.error("Il faut au moins 2 équipes pour faire un combat!"));
            return;
        }

        DisplayUtil.printSectionTitle("COMBAT D'ÉQUIPES AVEC STRATÉGIES");
        
        // Afficher toutes les équipes et armées disponibles
        List<TeamComposite> allGroups = new ArrayList<>();
        allGroups.addAll(teams);
        allGroups.addAll(armies);
        
        view.showMessage("\nÉquipes/Armées disponibles:");
        for (int i = 0; i < allGroups.size(); i++) {
            view.showMessage(i + ". " + allGroups.get(i));
        }
        
        int team1Index = view.askIntInput("\nChoisir l'équipe 1 (0-" + (allGroups.size()-1) + ")");
        int team2Index = view.askIntInput("Choisir l'équipe 2 (0-" + (allGroups.size()-1) + ")");
        
        if (team1Index < 0 || team1Index >= allGroups.size() ||
            team2Index < 0 || team2Index >= allGroups.size() ||
            team1Index == team2Index) {
            view.showMessage(DisplayUtil.error("Indices invalides"));
            return;
        }
        
        TeamComposite team1 = allGroups.get(team1Index);
        TeamComposite team2 = allGroups.get(team2Index);
        
        // Choisir les stratégies
        view.showMessage("\n📋 Stratégies disponibles:");
        view.showMessage("1. ⚔ Agressive - Attaque constamment");
        view.showMessage("2. 🛡 Défensive - Privilégie la défense et survie");
        view.showMessage("3. ⚖ Équilibrée - S'adapte à la situation");
        
        int strat1Choice = view.askIntInput("\nStratégie pour " + team1.getName() + " (1-3)");
        int strat2Choice = view.askIntInput("Stratégie pour " + team2.getName() + " (1-3)");
        
        CombatStrategy strategy1 = getStrategy(strat1Choice);
        CombatStrategy strategy2 = getStrategy(strat2Choice);
        
        // Lancer le combat
        teamBattle.startTeamBattle(team1, team2, strategy1, strategy2);
    }
    
    private CombatStrategy getStrategy(int choice) {
        switch (choice) {
            case 1: return new AggressiveStrategy();
            case 2: return new DefensiveStrategy();
            case 3: return new BalancedStrategy();
            default: return new BalancedStrategy();
        }
    }
}
