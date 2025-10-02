package com.rpg.dao;

import com.rpg.model.Character;
import java.util.List;

public interface CharacterDAO {
    void save(Character character);
    List<Character> loadAll();
}
