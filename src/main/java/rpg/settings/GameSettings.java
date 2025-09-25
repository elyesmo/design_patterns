package rpg.settings;

import rpg.core.Character;

public class GameSettings {
    private static GameSettings instance;
    private int maxStatPoints;

    private GameSettings() {
        this.maxStatPoints = 100;
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    public int getMaxStatPoints() {
        return maxStatPoints;
    }

    public void setMaxStatPoints(int maxStatPoints) {
        this.maxStatPoints = maxStatPoints;
    }

    public boolean isValid(Character character) {
        return character.getPowerLevel() <= maxStatPoints;
    }

    public String getValidationMessage(Character character) {
        if (isValid(character)) {
            return "Character is valid";
        } else {
            return String.format("Character exceeds maximum stat points. Current: %d, Max: %d", 
                    character.getPowerLevel(), maxStatPoints);
        }
    }
}