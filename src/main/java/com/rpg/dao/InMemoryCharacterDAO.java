package com.rpg.dao;

import com.rpg.model.Character;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of CharacterDAO.
 * Thread-safe using ConcurrentHashMap.
 */
public class InMemoryCharacterDAO implements DAO<Character> {
    private final Map<UUID, Character> storage = new ConcurrentHashMap<>();
    private final Map<String, UUID> nameIndex = new ConcurrentHashMap<>();

    @Override
    public UUID save(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        
        UUID id = UUID.randomUUID();
        storage.put(id, character);
        nameIndex.put(character.getName().toLowerCase(), id);
        return id;
    }

    @Override
    public Optional<Character> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Character> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean update(UUID id, Character character) {
        if (!storage.containsKey(id)) {
            return false;
        }
        
        // Update name index if name changed
        Character oldCharacter = storage.get(id);
        if (!oldCharacter.getName().equals(character.getName())) {
            nameIndex.remove(oldCharacter.getName().toLowerCase());
            nameIndex.put(character.getName().toLowerCase(), id);
        }
        
        storage.put(id, character);
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        Character removed = storage.remove(id);
        if (removed != null) {
            nameIndex.remove(removed.getName().toLowerCase());
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        storage.clear();
        nameIndex.clear();
    }

    /**
     * Finds a character by name (case-insensitive).
     * @param name The character's name
     * @return Optional containing the character if found
     */
    public Optional<Character> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        UUID id = nameIndex.get(name.toLowerCase());
        return id != null ? findById(id) : Optional.empty();
    }

    /**
     * Finds all characters sorted by name.
     * @return List of characters sorted alphabetically by name
     */
    public List<Character> findAllSortedByName() {
        return storage.values().stream()
            .sorted(Comparator.comparing(Character::getName))
            .collect(Collectors.toList());
    }

    /**
     * Finds all characters sorted by power level (descending).
     * @return List of characters sorted by power level
     */
    public List<Character> findAllSortedByPower() {
        return storage.values().stream()
            .sorted(Comparator.comparing(Character::getPowerLevel).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Finds all characters sorted by level (descending).
     * @return List of characters sorted by level
     */
    public List<Character> findAllSortedByLevel() {
        return storage.values().stream()
            .sorted(Comparator.comparing(Character::getLevel).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Checks if a character with the given name exists.
     * @param name The character's name
     * @return true if a character with that name exists
     */
    public boolean existsByName(String name) {
        return name != null && nameIndex.containsKey(name.toLowerCase());
    }

    /**
     * Gets the count of characters in storage.
     * @return The number of characters
     */
    public int count() {
        return storage.size();
    }
}

