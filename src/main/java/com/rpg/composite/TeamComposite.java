package com.rpg.composite;

import com.rpg.observer.TeamObserver;
import com.rpg.singleton.GameSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite du pattern Composite représentant une équipe
 * qui peut contenir des personnages ou d'autres équipes
 */
public class TeamComposite implements TeamComponent {
    private String name;
    private List<TeamComponent> members;
    private List<TeamObserver> observers;
    private String type; // "TEAM" ou "ARMY"

    public TeamComposite(String name, String type) {
        this.name = name;
        this.type = type;
        this.members = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Pattern Observer
    public void addObserver(TeamObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TeamObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String event) {
        for (TeamObserver observer : observers) {
            observer.update(this, event);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public int getPowerLevel() {
        return members.stream()
                .mapToInt(TeamComponent::getPowerLevel)
                .sum();
    }

    @Override
    public void display(String indent) {
        String icon = type.equals("ARMY") ? "🛡" : "👥";
        System.out.println(indent + icon + " " + name + " (" + type + ") - Puissance totale: " + 
                         getPowerLevel() + " - Membres: " + getMemberCount());
        for (TeamComponent member : members) {
            member.display(indent + "  ");
        }
    }

    @Override
    public List<TeamComponent> getChildren() {
        return Collections.unmodifiableList(members);
    }

    @Override
    public void add(TeamComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Cannot add null component");
        }
        
        // Check for duplicates
        if (members.contains(component)) {
            throw new IllegalStateException("Component " + component.getName() + " is already in " + name);
        }
        
        // Check for duplicate names
        for (TeamComponent existing : members) {
            if (existing.getName().equalsIgnoreCase(component.getName())) {
                throw new IllegalStateException("A component named '" + component.getName() + "' already exists in " + name);
            }
        }
        
        GameSettings settings = GameSettings.getInstance();
        
        // Validation selon le type
        if (type.equals("TEAM") && members.size() >= settings.getMaxCharactersPerTeam()) {
            throw new IllegalStateException("Équipe pleine (max " + settings.getMaxCharactersPerTeam() + " membres)");
        }
        
        if (type.equals("ARMY") && members.size() >= settings.getMaxTeamsPerArmy()) {
            throw new IllegalStateException("Armée pleine (max " + settings.getMaxTeamsPerArmy() + " équipes)");
        }
        
        members.add(component);
        notifyObservers("Ajout de " + component.getName() + " à " + name);
    }

    @Override
    public void remove(TeamComponent component) {
        if (members.remove(component)) {
            notifyObservers("Retrait de " + component.getName() + " de " + name);
        }
    }

    @Override
    public int getMemberCount() {
        return members.stream()
                .mapToInt(TeamComponent::getMemberCount)
                .sum();
    }

    public List<TeamComponent> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d membres, Puissance: %d)", 
                           type.equals("ARMY") ? "Armée" : "Équipe",
                           name, getMemberCount(), getPowerLevel());
    }
} 