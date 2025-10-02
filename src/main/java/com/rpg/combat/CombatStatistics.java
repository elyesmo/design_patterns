package com.rpg.combat;

import com.rpg.util.DisplayUtil;
import java.util.*;

/**
 * Enregistre et affiche les statistiques de combat
 */
public class CombatStatistics {
    private Map<String, Integer> actionsCount;
    private List<String> koList;
    private int totalActions;
    private long startTime;
    
    public CombatStatistics() {
        this.actionsCount = new HashMap<>();
        this.koList = new ArrayList<>();
        this.totalActions = 0;
    }
    
    public void reset() {
        actionsCount.clear();
        koList.clear();
        totalActions = 0;
        startTime = System.currentTimeMillis();
    }
    
    public void recordAction(String character, String action) {
        actionsCount.put(character, actionsCount.getOrDefault(character, 0) + 1);
        totalActions++;
    }
    
    public void recordKO(String character) {
        koList.add(character);
    }
    
    public void displayStatistics() {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        
        System.out.println("\n" + DisplayUtil.createBorder("📊 STATISTIQUES DU COMBAT 📊", 70));
        
        System.out.println(DisplayUtil.colorize("  Durée du combat: ", DisplayUtil.CYAN) + duration + " secondes");
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
        
        if (!koList.isEmpty()) {
            System.out.println("\n" + DisplayUtil.colorize("  K.O. (ordre):", DisplayUtil.RED));
            for (int i = 0; i < koList.size(); i++) {
                System.out.println("    " + (i+1) + ". " + koList.get(i));
            }
        }
        
        System.out.println("\n" + DisplayUtil.closeBorder(70));
    }
} 