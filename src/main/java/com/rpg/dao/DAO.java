package com.rpg.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Generic Data Access Object interface.
 * @param <T> The entity type
 */
public interface DAO<T> {
    
    /**
     * Saves or updates an entity.
     * @param entity The entity to save
     * @return The UUID of the saved entity
     */
    UUID save(T entity);
    
    /**
     * Finds an entity by its UUID.
     * @param id The entity's UUID
     * @return Optional containing the entity if found
     */
    Optional<T> findById(UUID id);
    
    /**
     * Finds all entities.
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Updates an existing entity.
     * @param id The entity's UUID
     * @param entity The updated entity
     * @return true if update was successful
     */
    boolean update(UUID id, T entity);
    
    /**
     * Removes an entity by its UUID.
     * @param id The entity's UUID
     * @return true if removal was successful
     */
    boolean remove(UUID id);
    
    /**
     * Removes all entities.
     */
    void clear();
}

