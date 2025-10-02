package com.rpg.combat;

import com.rpg.command.Command;
import com.rpg.composite.*;
import com.rpg.model.Character;
import com.rpg.observer.CombatLogger;
import com.rpg.strategy.*;
import com.rpg.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère les combats d'équipes avec stratégies et affichage amélioré
 */
public class TeamBattle {
    private CombatLogger logger;
    private CombatStatistics stats;
    
    public TeamBattle(CombatLogger logger) {
        this.logger = logger;
        this.stats = new CombatStatistics();
    }
    
    /**
     * Combat entre deux équipes
     */
    public void startTeamBattle(TeamComposite team1, TeamComposite team2, 
                                CombatStrategy strategy1, CombatStrategy strategy2) {
        
        List<Character> fighters1 = extractCharacters(team1);
        List<Character> fighters2 = extractCharacters(team2);
        
        if (fighters1.isEmpty() || fighters2.isEmpty()) {
            System.out.println(DisplayUtil.error("Les équipes doivent avoir au moins un combattant!"));
            return;
        }
        
        // Réinitialiser les statistiques
        stats.reset();
        
        // Affichage de l'introduction
        displayBattleIntro(team1, team2, fighters1, fighters2, strategy1, strategy2);
        
        int round = 1;
        boolean continueChronique = true;
        
        while (!fighters1.isEmpty() && !fighters2.isEmpty() && continueChronique) {
            DisplayUtil.printSeparator();
            System.out.println(DisplayUtil.colorize("\n═══ ROUND " + round + " ═══", DisplayUtil.BOLD + DisplayUtil.YELLOW));
            DisplayUtil.printSeparator();
            
            // Afficher l'état des équipes
            displayTeamStatus(team1.getName(), fighters1);
            displayTeamStatus(team2.getName(), fighters2);
            
            // Tour de l'équipe 1
            System.out.println(DisplayUtil.colorize("\n▶ Tour de " + team1.getName(), DisplayUtil.CYAN));
            executeTurn(fighters1, fighters2, strategy1);
            
            // Retirer les KO de l'équipe 2
            removeKnockedOut(fighters2);
            
            if (fighters2.isEmpty()) {
                declareWinner(team1, fighters1);
                break;
            }
            
            // Tour de l'équipe 2
            System.out.println(DisplayUtil.colorize("\n▶ Tour de " + team2.getName(), DisplayUtil.PURPLE));
            executeTurn(fighters2, fighters1, strategy2);
            
            // Retirer les KO de l'équipe 1
            removeKnockedOut(fighters1);
            
            if (fighters1.isEmpty()) {
                declareWinner(team2, fighters2);
                break;
            }
            
            // Demander si on continue (tous les 3 rounds)
            if (round % 3 == 0) {
                System.out.print(DisplayUtil.info("\nContinuer le combat? (o/n): "));
                try {
                    String input = new java.util.Scanner(System.in).nextLine();
                    if (input.equalsIgnoreCase("n")) {
                        continueChronique = false;
                        displayDrawResult(fighters1, fighters2);
                    }
                } catch (Exception e) {
                    // Continuer par défaut
                }
            }
            
            round++;
        }
        
        // Afficher les statistiques finales
        stats.displayStatistics();
    }
    
    /**
     * Extrait tous les personnages d'une équipe ou armée
     */
    private List<Character> extractCharacters(TeamComponent team) {
        List<Character> characters = new ArrayList<>();
        
        if (team instanceof CharacterLeaf) {
            characters.add(((CharacterLeaf) team).getCharacter());
        } else if (team instanceof TeamComposite) {
            for (TeamComponent child : team.getChildren()) {
                characters.addAll(extractCharacters(child));
            }
        }
        
        return characters;
    }
    
    /**
     * Affiche l'introduction du combat
     */
    private void displayBattleIntro(TeamComposite team1, TeamComposite team2,
                                    List<Character> fighters1, List<Character> fighters2,
                                    CombatStrategy strategy1, CombatStrategy strategy2) {
        System.out.println(DisplayUtil.createBorder("⚔ COMBAT D'ÉQUIPES ⚔", 70));
        
        System.out.println(DisplayUtil.colorize("  " + team1.getName() + " [" + fighters1.size() + " combattants]", 
                                                DisplayUtil.CYAN + DisplayUtil.BOLD));
        System.out.println("    Stratégie: " + strategy1.getStrategyName());
        System.out.println("    " + strategy1.getDescription());
        
        System.out.println(DisplayUtil.colorize("\n               🆚 CONTRE 🆚\n", DisplayUtil.YELLOW));
        
        System.out.println(DisplayUtil.colorize("  " + team2.getName() + " [" + fighters2.size() + " combattants]", 
                                                DisplayUtil.PURPLE + DisplayUtil.BOLD));
        System.out.println("    Stratégie: " + strategy2.getStrategyName());
        System.out.println("    " + strategy2.getDescription());
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        
        logger.logCombatEvent("Début du combat: " + team1.getName() + " vs " + team2.getName());
    }
    
    /**
     * Affiche l'état d'une équipe
     */
    private void displayTeamStatus(String teamName, List<Character> fighters) {
        System.out.println("\n📊 " + DisplayUtil.colorize(teamName, DisplayUtil.BOLD));
        for (int i = 0; i < fighters.size(); i++) {
            Character fighter = fighters.get(i);
            System.out.println("  " + (i + 1) + ". " + fighter.getName() + " [Niv." + fighter.getLevel() + "] " +
                             DisplayUtil.createHealthBar(fighter.getHealth(), fighter.getMaxHealth(), 20));
        }
    }
    
    /**
     * Exécute le tour d'une équipe
     */
    private void executeTurn(List<Character> attackers, List<Character> defenders, CombatStrategy strategy) {
        for (Character attacker : attackers) {
            if (defenders.isEmpty()) break;
            if (attacker.getHealth() <= 0) continue;
            
            // Choisir une cible aléatoire parmi les défenseurs vivants
            List<Character> aliveDefenders = new ArrayList<>();
            for (Character def : defenders) {
                if (def.getHealth() > 0) aliveDefenders.add(def);
            }
            
            if (aliveDefenders.isEmpty()) break;
            
            Character target = aliveDefenders.get((int)(Math.random() * aliveDefenders.size()));
            
            // Utiliser la stratégie pour choisir l'action
            Command action = strategy.chooseAction(attacker, target);
            
            if (action != null) {
                System.out.println("\n  → " + attacker.getName() + " agit:");
                action.execute();
                stats.recordAction(attacker.getName(), action.toString());
                
                // Pause visuelle
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        }
    }
    
    /**
     * Retire les combattants KO
     */
    private void removeKnockedOut(List<Character> fighters) {
        List<Character> toRemove = new ArrayList<>();
        for (Character fighter : fighters) {
            if (fighter.getHealth() <= 0) {
                System.out.println(DisplayUtil.colorize("\n  💀 " + fighter.getName() + " est K.O.!", DisplayUtil.RED));
                logger.logCombatEvent(fighter.getName() + " est K.O.");
                toRemove.add(fighter);
                stats.recordKO(fighter.getName());
            }
        }
        fighters.removeAll(toRemove);
    }
    
    /**
     * Déclare le vainqueur
     */
    private void declareWinner(TeamComposite winner, List<Character> survivors) {
        System.out.println("\n" + DisplayUtil.createBorder("🏆 VICTOIRE 🏆", 70));
        System.out.println(DisplayUtil.colorize("  " + winner.getName() + " remporte le combat!", 
                                                DisplayUtil.GREEN + DisplayUtil.BOLD));
        
        System.out.println("\n  Survivants:");
        for (Character survivor : survivors) {
            System.out.println("    ✓ " + survivor.getName() + " - " + 
                             DisplayUtil.createHealthBar(survivor.getHealth(), survivor.getMaxHealth(), 15));
            // Donner de l'expérience
            survivor.addExperience(50);
            if (survivor.getExperience() >= 0) {
                System.out.println("      +" + 50 + " XP (Total: " + survivor.getExperience() + "/100)");
            }
        }
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        logger.logCombatEvent(winner.getName() + " a gagné le combat!");
    }
    
    /**
     * Affiche un résultat d'égalité
     */
    private void displayDrawResult(List<Character> team1, List<Character> team2) {
        System.out.println("\n" + DisplayUtil.createBorder("⚖ MATCH NUL ⚖", 70));
        System.out.println(DisplayUtil.colorize("  Le combat s'arrête avec les deux équipes encore debout!", 
                                                DisplayUtil.YELLOW + DisplayUtil.BOLD));
        System.out.println("\n" + DisplayUtil.closeBorder(70));
        logger.logCombatEvent("Combat interrompu - Match nul");
    }
} 