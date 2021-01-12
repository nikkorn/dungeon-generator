package com.dumbpug.dungeony.engine;

/**
 * Enumeration of entity update strategy types.
 */
public enum EntityUpdateStrategy {
    /**
     * The entity should never be updated.
     */
    NONE,

    /**
     * The entity should be updated once per environment update.
     */
    PER_UPDATE,

    /**
     * The entity should be updated after a delay.
     */
    DELAY
}
