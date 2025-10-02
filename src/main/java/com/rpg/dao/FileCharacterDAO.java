package com.rpg.dao;

import com.rpg.model.Character;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCharacterDAO implements CharacterDAO {
    private String filename = "characters.txt";

    @Override
    public void save(Character character) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(character.getName() + ";" + character.getStrength() + 
                         ";" + character.getAgility() + ";" + character.getIntelligence());
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde");
        }
    }

    @Override
    public List<Character> loadAll() {
        List<Character> characters = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    Character c = new Character(parts[0], 
                                              Integer.parseInt(parts[1]),
                                              Integer.parseInt(parts[2]),
                                              Integer.parseInt(parts[3]));
                    characters.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Aucune sauvegarde trouvee");
        }
        return characters;
    }
}
