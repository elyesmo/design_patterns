package rpg.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Party {
    private List<Character> characters;

    public Party() {
        this.characters = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public boolean removeCharacter(String name) {
        return characters.removeIf(character -> character.getName().equals(name));
    }

    public int getTotalPower() {
        return characters.stream().mapToInt(Character::getPowerLevel).sum();
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public List<Character> getCharactersSortedByPower() {
        return characters.stream()
                .sorted(Comparator.comparingInt(Character::getPowerLevel).reversed())
                .toList();
    }

    public List<Character> getCharactersSortedByName() {
        return characters.stream()
                .sorted(Comparator.comparing(Character::getName))
                .toList();
    }

    public int size() {
        return characters.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Party:\n");
        for (Character character : characters) {
            sb.append("  - ").append(character.getDescription()).append("\n");
        }
        sb.append("Total Power: ").append(getTotalPower());
        return sb.toString();
    }
}