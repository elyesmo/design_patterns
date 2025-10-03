package com.rpg.combat;

import com.rpg.util.DisplayUtil;
import java.util.*;

/**
 * Enregistre et affiche les statistiques de combat.
 * 
 * Enhanced with:
 * - Total damage dealt per character
 * - DPS (damage per second)
 * - Critical hits counter
 * - Turn counter
 */
public class CombatStatistics {
    private Map<String, Integer> actionsCount;
    private Map<String, Integer> damageDealt;
    private Map<String, Integer> criticalHits;
    private List<String> koList;
    private int totalActions;
    private int totalTurns;
    private long startTime;
    private static final int MAX_TURNS = 100; // Stalemate threshold
    
    public CombatStatistics() {
        this.actionsCount = new HashMap<>();
        this.damageDealt = new HashMap<>();
        this.criticalHits = new HashMap<>();
        this.koList = new ArrayList<>();
        this.totalActions = 0;
        this.totalTurns = 0;
    }
    
    public void reset() {
        actionsCount.clear();
        damageDealt.clear();
        criticalHits.clear();
        koList.clear();
        totalActions = 0;
        totalTurns = 0;
        startTime = System.currentTimeMillis();
    }
    
    public void recordAction(String character, String action) {
        actionsCount.put(character, actionsCount.getOrDefault(character, 0) + 1);
        totalActions++;
    }
    
    public void recordDamage(String character, int damage) {
        damageDealt.put(character, damageDealt.getOrDefault(character, 0) + damage);
    }
    
    public void recordCriticalHit(String character) {
        criticalHits.put(character, criticalHits.getOrDefault(character, 0) + 1);
    }
    
    public void recordKO(String character) {
        koList.add(character);
    }
    
    public void incrementTurn() {
        totalTurns++;
    }
    
    public int getTurns() {
        return totalTurns;
    }
    
    public boolean isStalemate() {
        return totalTurns >= MAX_TURNS;
    }
    
    public int getMaxTurns() {
        return MAX_TURNS;
    }
    
    public void displayStatistics() {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        
        System.out.println("\n" + DisplayUtil.createBorder("📊 STATISTIQUES DU COMBAT 📊", 70));
        
        System.out.println(DisplayUtil.colorize("  Durée du combat: ", DisplayUtil.CYAN) + duration + " secondes");
        System.out.println(DisplayUtil.colorize("  Nombre de tours: ", DisplayUtil.CYAN) + totalTurns);
        System.out.println(DisplayUtil.colorize("  Total d'actions: ", DisplayUtil.CYAN) + totalActions);
        
        if (!actionsCount.isEmpty()) {
            System.out.println("\n" + DisplayUtil.colorize("  Actions par combattant:", DisplayUtil.BOLD));
            
            // Trier par nombre d'actions
            List<Map.Entry<String, Integer>> sorted = new ArrayList<>(actionsCount.entrySet());
            sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            
            for (Map.Entry<String, Integer> entry : sorted) {
                int percentage = (int)((double)entry.getValue() / totalActions * 100);
                System.out.println(String.format("    • %-20s: %2d actions (%d%%)", 
                    entry.getKey(), entry.getValue(), percentage));
            }
        }
        
        if (!damageDealt.isEmpty()) {
            System.out.println("\n" + DisplayUtil.colorize("  Dégâts infligés:", DisplayUtil.BOLD));
            
            List<Map.Entry<String, Integer>> sortedDamage = new ArrayList<>(damageDealt.entrySet());
            sortedDamage.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            
            for (Map.Entry<String, Integer> entry : sortedDamage) {
                int dps = duration > 0 ? entry.getValue() / (int)duration : entry.getValue();
                String crits = criticalHits.containsKey(entry.getKey()) ? 
                    " (★" + criticalHits.get(entry.getKey()) + " crits)" : "";
                System.out.println(String.format("    • %-20s: %3d dégâts (DPS: %d)%s", 
                    entry.getKey(), entry.getValue(), dps, crits));
            }
        }
        
        if (!koList.isEmpty()) {
            System.out.println("\n" + DisplayUtil.colorize("  K.O. (ordre):", DisplayUtil.RED));
            for (int i = 0; i < koList.size(); i++) {
                System.out.println("    " + (i+1) + ". " + koList.get(i));
            }
        }
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
    }
} 