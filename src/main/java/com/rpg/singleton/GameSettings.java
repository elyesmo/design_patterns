package com.rpg.singleton;

public class GameSettings {
    private static GameSettings instance;
    private int maxStatPoints = 50;
    private int maxCharactersPerTeam = 5;
    private int maxAbilities = 3;
    private int maxTeamsPerArmy = 4;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public int getMaxStatPoints() { return maxStatPoints; }
    public int getMaxCharactersPerTeam() { return maxCharactersPerTeam; }
    public int getMaxAbilities() { return maxAbilities; }
    public int getMaxTeamsPerArmy() { return maxTeamsPerArmy; }
    
    public void setMaxStatPoints(int maxStatPoints) { this.maxStatPoints = maxStatPoints; }
    public void setMaxCharactersPerTeam(int max) { this.maxCharactersPerTeam = max; }
    public void setMaxAbilities(int max) { this.maxAbilities = max; }
    public void setMaxTeamsPerArmy(int max) { this.maxTeamsPerArmy = max; }
}
