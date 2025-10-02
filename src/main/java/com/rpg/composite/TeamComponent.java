package com.rpg.composite;

import java.util.List;

/**
 * Interface du pattern Composite pour gérer hiérarchiquement 
 * les personnages, équipes et armées
 */
public interface TeamComponent {
    String getName();
    int getPowerLevel();
    void display(String indent);
    List<TeamComponent> getChildren();
    void add(TeamComponent component);
    void remove(TeamComponent component);
    int getMemberCount();
} 