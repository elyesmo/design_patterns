package com.rpg.util;

/**
 * Utilitaire pour améliorer l'affichage console avec barres de vie,
 * couleurs ANSI, bordures et formatage
 */
public class DisplayUtil {
    
    // Codes couleurs ANSI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    // Barres et symboles
    public static final String HEALTH_FULL = "█";
    public static final String HEALTH_EMPTY = "░";
    
    /**
     * Crée une barre de vie visuelle
     */
    public static String createHealthBar(int current, int max, int barLength) {
        if (max <= 0) return "";
        
        double percentage = (double) current / max;
        int filled = (int) (percentage * barLength);
        
        StringBuilder bar = new StringBuilder();
        
        // Couleur selon le pourcentage
        if (percentage > 0.6) {
            bar.append(GREEN);
        } else if (percentage > 0.3) {
            bar.append(YELLOW);
        } else {
            bar.append(RED);
        }
        
        // Barre remplie
        for (int i = 0; i < filled; i++) {
            bar.append(HEALTH_FULL);
        }
        
        // Barre vide
        bar.append(WHITE);
        for (int i = filled; i < barLength; i++) {
            bar.append(HEALTH_EMPTY);
        }
        
        bar.append(RESET);
        bar.append(String.format(" %d/%d PV (%.0f%%)", current, max, percentage * 100));
        
        return bar.toString();
    }
    
    /**
     * Crée une bordure décorative
     */
    public static String createBorder(String title, int width) {
        StringBuilder border = new StringBuilder();
        border.append(CYAN).append(BOLD);
        border.append("╔");
        for (int i = 0; i < width - 2; i++) border.append("═");
        border.append("╗\n");
        
        if (title != null && !title.isEmpty()) {
            int padding = (width - title.length() - 2) / 2;
            border.append("║");
            for (int i = 0; i < padding; i++) border.append(" ");
            border.append(title);
            for (int i = 0; i < width - title.length() - padding - 2; i++) border.append(" ");
            border.append("║\n");
            
            border.append("╠");
            for (int i = 0; i < width - 2; i++) border.append("═");
            border.append("╣\n");
        }
        
        border.append(RESET);
        return border.toString();
    }
    
    /**
     * Ferme une bordure
     */
    public static String closeBorder(int width) {
        StringBuilder border = new StringBuilder();
        border.append(CYAN).append(BOLD);
        border.append("╚");
        for (int i = 0; i < width - 2; i++) border.append("═");
        border.append("╝");
        border.append(RESET);
        return border.toString();
    }
    
    /**
     * Formate un texte en couleur
     */
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    /**
     * Affiche un message de combat stylisé
     */
    public static String combatMessage(String attacker, String action, String target, String color) {
        return String.format("%s⚔ %s%s %s%s → %s%s%s",
            color, BOLD, attacker, RESET, color, action, target, RESET);
    }
    
    /**
     * Crée un tableau de statistiques
     */
    public static String createStatsTable(String name, int str, int agi, int intel, int health, int maxHealth) {
        StringBuilder table = new StringBuilder();
        table.append(String.format("┌─ %s%s%s ─┐\n", BOLD, name, RESET));
        table.append(String.format("│ ❤  Santé       : %s\n", createHealthBar(health, maxHealth, 15)));
        table.append(String.format("│ ⚔  Force       : %s%d%s\n", YELLOW, str, RESET));
        table.append(String.format("│ 🏃 Agilité     : %s%d%s\n", CYAN, agi, RESET));
        table.append(String.format("│ 🧠 Intelligence: %s%d%s\n", PURPLE, intel, RESET));
        table.append("└────────────────────────────────────┘");
        return table.toString();
    }
    
    /**
     * Affiche une animation de séparation
     */
    public static void printSeparator() {
        System.out.println(CYAN + "═".repeat(60) + RESET);
    }
    
    /**
     * Affiche un titre de section
     */
    public static void printSectionTitle(String title) {
        System.out.println("\n" + BOLD + CYAN + "▼ " + title.toUpperCase() + RESET);
        System.out.println(CYAN + "─".repeat(60) + RESET);
    }
    
    /**
     * Message de succès
     */
    public static String success(String message) {
        return GREEN + "✓ " + message + RESET;
    }
    
    /**
     * Message d'erreur
     */
    public static String error(String message) {
        return RED + "✗ " + message + RESET;
    }
    
    /**
     * Message d'avertissement
     */
    public static String warning(String message) {
        return YELLOW + "⚠ " + message + RESET;
    }
    
    /**
     * Message d'information
     */
    public static String info(String message) {
        return BLUE + "ℹ " + message + RESET;
    }
} 