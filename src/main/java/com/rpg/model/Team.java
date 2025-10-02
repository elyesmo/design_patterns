package com.rpg.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Character> members;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Character> getMembers() { return members; }
    public void addMember(Character character) { members.add(character); }
    public void removeMember(Character character) { members.remove(character); }

    public int getTotalPower() {
        return members.stream().mapToInt(Character::getPowerLevel).sum();
    }

    @Override
    public String toString() {
        return String.format("Equipe %s (%d membres, Puissance totale: %d)", 
                           name, members.size(), getTotalPower());
    }
}
