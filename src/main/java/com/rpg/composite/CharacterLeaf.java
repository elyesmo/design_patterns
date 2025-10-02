package com.rpg.composite;

import com.rpg.model.Character;
import java.util.ArrayList;
import java.util.List;

/**
 * Feuille du pattern Composite représentant un personnage individuel
 */
public class CharacterLeaf implements TeamComponent {
    private Character character;

    public CharacterLeaf(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public String getName() {
        return character.getName();
    }

    @Override
    public int getPowerLevel() {
        return character.getPowerLevel();
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "⚔ " + character.toString() + " (Puissance: " + character.getPowerLevel() + ")");
    }

    @Override
    public List<TeamComponent> getChildren() {
        return new ArrayList<>(); // Une feuille n'a pas d'enfants
    }

    @Override
    public void add(TeamComponent component) {
        throw new UnsupportedOperationException("Impossible d'ajouter un composant à un personnage");
    }

    @Override
    public void remove(TeamComponent component) {
        throw new UnsupportedOperationException("Impossible de retirer un composant d'un personnage");
    }

    @Override
    public int getMemberCount() {
        return 1;
    }
} 