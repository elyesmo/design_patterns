package com.rpg.observer;

import com.rpg.composite.TeamComposite;

/**
 * Interface Observer pour être notifié des changements dans les équipes
 */
public interface TeamObserver {
    void update(TeamComposite team, String event);
} 