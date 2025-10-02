package com.rpg.observer;

import com.rpg.composite.TeamComposite;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer concret qui enregistre tous les événements
 */
public class CombatLogger implements TeamObserver {
    private List<String> logs;
    private DateTimeFormatter formatter;

    public CombatLogger() {
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    public void update(TeamComposite team, String event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] %s - %s", timestamp, team.getName(), event);
        logs.add(logEntry);
        System.out.println("📋 LOG: " + logEntry);
    }

    public void logCombatEvent(String event) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] COMBAT: %s", timestamp, event);
        logs.add(logEntry);
        System.out.println("⚔ " + logEntry);
    }

    public void displayLogs() {
        System.out.println("\n=== JOURNAL DE COMBAT ===");
        if (logs.isEmpty()) {
            System.out.println("Aucun événement enregistré");
        } else {
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }

    public void clearLogs() {
        logs.clear();
        System.out.println("Journal effacé");
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }
} 