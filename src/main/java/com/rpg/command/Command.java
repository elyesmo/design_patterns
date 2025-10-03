package com.rpg.command;

/**
 * Command pattern interface.
 * Commands can be executed and converted to DTOs for safe replay.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();
    
    /**
     * Converts the command to a DTO for safe replay.
     * Default implementation returns a generic DTO with the command's toString().
     * @return ActionDTO representing this command
     */
    default ActionDTO toDTO() {
        return new ActionDTO("GENERIC")
            .addArg("description", toString());
    }
}
