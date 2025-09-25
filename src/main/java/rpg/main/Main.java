package rpg.main;

import rpg.builder.CharacterBuilder;
import rpg.core.Character;
import rpg.core.Party;
import rpg.dao.CharacterDAO;
import rpg.decorator.FireResistance;
import rpg.decorator.Invisibility;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Character Generator ===\n");

        // Initialize components
        CharacterBuilder builder = new CharacterBuilder();
        CharacterDAO dao = new CharacterDAO();
        GameSettings settings = GameSettings.getInstance();
        Party party = new Party();

        // Create characters using Builder pattern
        Character warrior = builder
                .setName("Aragorn")
                .setStrength(30)
                .setAgility(20)
                .setIntelligence(15)
                .build();

        Character mage = builder.reset()
                .setName("Gandalf")
                .setStrength(10)
                .setAgility(15)
                .setIntelligence(35)
                .build();

        Character rogue = builder.reset()
                .setName("Legolas")
                .setStrength(20)
                .setAgility(35)
                .setIntelligence(10)
                .build();

        // Add decorators (special abilities)
        Character enhancedWarrior = new FireResistance(new Invisibility(warrior));
        Character enhancedMage = new Telepathy(mage);
        Character enhancedRogue = new Invisibility(rogue);

        // Validate characters
        System.out.println("=== Character Validation ===");
        System.out.println(settings.getValidationMessage(enhancedWarrior));
        System.out.println(settings.getValidationMessage(enhancedMage));
        System.out.println(settings.getValidationMessage(enhancedRogue));
        System.out.println();

        // Save characters to DAO
        dao.save(enhancedWarrior);
        dao.save(enhancedMage);
        dao.save(enhancedRogue);

        // Display all characters
        System.out.println("=== All Characters ===");
        for (Character character : dao.findAll()) {
            System.out.println(character.getDescription());
        }
        System.out.println();

        // Create and display party
        party.addCharacter(enhancedWarrior);
        party.addCharacter(enhancedMage);
        party.addCharacter(enhancedRogue);

        System.out.println("=== Party Information ===");
        System.out.println(party);
        System.out.println();

        // Sort characters
        System.out.println("=== Characters Sorted by Power ===");
        for (Character character : party.getCharactersSortedByPower()) {
            System.out.println(character.getDescription());
        }
        System.out.println();

        System.out.println("=== Characters Sorted by Name ===");
        for (Character character : party.getCharactersSortedByName()) {
            System.out.println(character.getDescription());
        }
        System.out.println();

        // Simple combat simulation
        System.out.println("=== Combat Simulation ===");
        simulateCombat(enhancedWarrior, enhancedMage);
    }

    private static void simulateCombat(Character char1, Character char2) {
        System.out.printf("Combat between %s (Power: %d) and %s (Power: %d)%n",
                char1.getName(), char1.getPowerLevel(),
                char2.getName(), char2.getPowerLevel());

        if (char1.getPowerLevel() > char2.getPowerLevel()) {
            System.out.printf("%s wins!%n", char1.getName());
        } else if (char2.getPowerLevel() > char1.getPowerLevel()) {
            System.out.printf("%s wins!%n", char2.getName());
        } else {
            System.out.println("It's a tie!");
        }
    }
}