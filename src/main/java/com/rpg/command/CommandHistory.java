package com.rpg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gère l'historique des commandes pour pouvoir les rejouer.
 * 
 * Improvements:
 * - Stores both Command instances and ActionDTOs
 * - ActionDTOs provide safe replay without side effects
 * - Provides access to immutable history
 */
public class CommandHistory {
    private List<Command> commandHistory;
    private List<ActionDTO> dtoHistory;

    public CommandHistory() {
        this.commandHistory = new ArrayList<>();
        this.dtoHistory = new ArrayList<>();
    }

    /**
     * Adds a command to history and stores its DTO representation.
     * @param command The command to add
     */
    public void addCommand(Command command) {
        commandHistory.add(command);
        dtoHistory.add(command.toDTO());
    }

    /**
     * Replays all commands by re-executing them.
     * WARNING: This may cause side effects. Consider using getDTOHistory() instead.
     */
    public void replay() {
        if (commandHistory.isEmpty()) {
            System.out.println("Aucune action à rejouer");
            return;
        }

        System.out.println("\n=== REJOUER L'HISTORIQUE ===");
        System.out.println("⚠ Note: Le replay réexécute les commandes et peut causer des effets de bord");
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println("\n--- Action " + (i + 1) + " ---");
            commandHistory.get(i).execute();
        }
    }

    /**
     * Displays the command history.
     */
    public void displayHistory() {
        if (commandHistory.isEmpty()) {
            System.out.println("Historique vide");
            return;
        }

        System.out.println("\n=== HISTORIQUE DES ACTIONS ===");
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println((i + 1) + ". " + commandHistory.get(i).toString());
        }
    }

    /**
     * Displays the DTO history (safe representation without side effects).
     */
    public void displayDTOHistory() {
        if (dtoHistory.isEmpty()) {
            System.out.println("Historique vide");
            return;
        }

        System.out.println("\n=== HISTORIQUE DES ACTIONS (DTO) ===");
        for (int i = 0; i < dtoHistory.size(); i++) {
            System.out.println((i + 1) + ". " + dtoHistory.get(i).toString());
        }
    }

    /**
     * Gets an immutable view of the DTO history.
     * @return Immutable list of ActionDTOs
     */
    public List<ActionDTO> getDTOHistory() {
        return Collections.unmodifiableList(dtoHistory);
    }

    /**
     * Clears all history.
     */
    public void clear() {
        commandHistory.clear();
        dtoHistory.clear();
        System.out.println("Historique effacé");
    }

    /**
     * Gets the size of the history.
     * @return Number of commands in history
     */
    public int size() {
        return commandHistory.size();
    }
} 