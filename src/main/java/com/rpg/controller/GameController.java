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
        view.showMessage("\n=== CRÉATION D'UN PERSONNAGE ===");
        String name = view.askInput("Nom du personnage");
        
        GameSettings settings = GameSettings.getInstance();
        view.showMessage("Points totaux maximum: " + settings.getMaxStatPoints());
        
        int strength = view.askIntInput("Force (1-20)");
        int agility = view.askIntInput("Agilité (1-20)");
        int intelligence = view.askIntInput("Intelligence (1-20)");

        Character character = new CharacterBuilder()
            .setName(name)
            .setStrength(strength)
            .setAgility(agility)
            .setIntelligence(intelligence)
            .build();

        if (validator.validate(character)) {
            characters.add(character);
            view.showMessage("✅ Personnage créé: " + character);
        } else {
            view.showMessage("❌ Personnage invalide (vérifiez les stats et le nom)");
        }
    }

    private void addAbilities() {
        if (characters.isEmpty()) {
            view.showMessage("❌ Aucun personnage disponible");
            return;
        }

        view.showMessage("\n=== AJOUT DE CAPACITÉS ===");
        showCharacters();
        int index = view.askIntInput("Index du personnage (0-" + (characters.size()-1) + ")");

        if (index >= 0 && index < characters.size()) {
            Character character = characters.get(index);

            view.showMessage("\n📚 Capacités disponibles:");
            view.showMessage("  1. Invisibilité (+5 Agilité)");
            view.showMessage("  2. Télépathie (+5 Intelligence)");
            view.showMessage("  3. Super Force (+8 Force)");
            view.showMessage("  4. Régénération (+50 PV)");
            view.showMessage("  5. Pouvoir du Feu (+3 Force, +3 Intelligence)");
            int ability = view.askIntInput("Choix capacité (1-5)");

            switch (ability) {
                case 1: AbilityDecorator.addInvisibility(character); break;
                case 2: AbilityDecorator.addTelepathy(character); break;
                case 3: AbilityDecorator.addSuperStrength(character); break;
                case 4: AbilityDecorator.addRegeneration(character); break;
                case 5: AbilityDecorator.addFirePower(character); break;
                default: view.showMessage("❌ Capacité invalide"); return;
            }

            if (validator.validate(character)) {
                view.showMessage("✅ Capacité ajoutée: " + character);
            } else {
                character.getAbilities().remove(character.getAbilities().size() - 1);
                view.showMessage("❌ Trop de capacités (max " + 
                               GameSettings.getInstance().getMaxAbilities() + ")");
            }
        }
    }

    private void showCharacters() {
        if (characters.isEmpty()) {
            view.showMessage("📋 Aucun personnage créé");
            return;
        }

        view.showMessage("\n═══ LISTE DES PERSONNAGES ═══");
        for (int i = 0; i < characters.size(); i++) {
            view.showMessage(i + ". " + characters.get(i));
        }
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
            view.showMessage("❌ Il faut au moins 2 personnages");
            return;
        }

        view.showMessage("\n⚔ === COMBAT SIMPLE ===");
        showCharacters();
        int attacker = view.askIntInput("Index attaquant (0-" + (characters.size()-1) + ")");
        int target = view.askIntInput("Index défenseur (0-" + (characters.size()-1) + ")");

        if (attacker >= 0 && attacker < characters.size() && 
            target >= 0 && target < characters.size() && attacker != target) {

            Command attack = new AttackCommand(characters.get(attacker), characters.get(target));
            attack.execute();
            commandHistory.addCommand(attack);
            
            combatLogger.logCombatEvent("Combat entre " + characters.get(attacker).getName() + 
                                       " et " + characters.get(target).getName());

            Character winner = characters.get(attacker).getPowerLevel() > characters.get(target).getPowerLevel() 
                             ? characters.get(attacker) : characters.get(target);
            view.showMessage("🏆 Le plus puissant est: " + winner.getName());
        } else {
            view.showMessage("❌ Indices invalides");
        }
    }

    private void advancedCombat() {
        if (characters.size() < 2) {
            view.showMessage("❌ Il faut au moins 2 personnages");
            return;
        }

        view.showMessage("\n⚔ === COMBAT AVANCÉ ===");
        showCharacters();
        int char1Index = view.askIntInput("Personnage 1 (0-" + (characters.size()-1) + ")");
        int char2Index = view.askIntInput("Personnage 2 (0-" + (characters.size()-1) + ")");

        if (char1Index < 0 || char1Index >= characters.size() || 
            char2Index < 0 || char2Index >= characters.size() || 
            char1Index == char2Index) {
            view.showMessage("❌ Indices invalides");
            return;
        }

        Character char1 = characters.get(char1Index);
        Character char2 = characters.get(char2Index);

        combatLogger.logCombatEvent("Début du combat entre " + char1.getName() + 
                                   " et " + char2.getName());

        boolean fighting = true;
        int round = 1;

        while (fighting && char1.getHealth() > 0 && char2.getHealth() > 0) {
            view.showMessage("\n--- ROUND " + round + " ---");
            view.showMessage(char1.getName() + ": " + char1.getHealth() + " PV");
            view.showMessage(char2.getName() + ": " + char2.getHealth() + " PV");

            // Tour du personnage 1
            Command cmd1 = chooseAction(char1, char2);
            if (cmd1 != null) {
                cmd1.execute();
                commandHistory.addCommand(cmd1);
            }

            if (char2.getHealth() <= 0) {
                view.showMessage("\n🏆 " + char1.getName() + " a gagné!");
                combatLogger.logCombatEvent(char1.getName() + " a vaincu " + char2.getName());
                break;
            }

            // Tour du personnage 2
            Command cmd2 = chooseAction(char2, char1);
            if (cmd2 != null) {
                cmd2.execute();
                commandHistory.addCommand(cmd2);
            }

            if (char1.getHealth() <= 0) {
                view.showMessage("\n🏆 " + char2.getName() + " a gagné!");
                combatLogger.logCombatEvent(char2.getName() + " a vaincu " + char1.getName());
                break;
            }

            view.showMessage("\nContinuer? (o/n)");
            String choice = view.getUserInput();
            if (choice.equalsIgnoreCase("n")) {
                fighting = false;
            }

            round++;
        }
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
