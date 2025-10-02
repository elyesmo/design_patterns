package com.rpg.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère l'historique des commandes pour pouvoir les rejouer
 */
public class CommandHistory {
    private List<Command> history;

    public CommandHistory() {
        this.history = new ArrayList<>();
    }

    public void addCommand(Command command) {
        history.add(command);
    }

    public void replay() {
        if (history.isEmpty()) {
            System.out.println("Aucune action à rejouer");
            return;
        }

        System.out.println("\n=== REJOUER L'HISTORIQUE ===");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("\n--- Action " + (i + 1) + " ---");
            history.get(i).execute();
        }
    }

    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("Historique vide");
            return;
        }

        System.out.println("\n=== HISTORIQUE DES ACTIONS ===");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i).toString());
        }
    }

    public void clear() {
        history.clear();
        System.out.println("Historique effacé");
    }

    public int size() {
        return history.size();
    }
} 