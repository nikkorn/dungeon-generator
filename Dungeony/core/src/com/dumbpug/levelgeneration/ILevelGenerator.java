package com.dumbpug.levelgeneration;

/**
 * Generates levels based on level resource files.
 */
public interface ILevelGenerator {
    /**
     * Generates a level definition.
     * @return A level definition.
     */
    LevelDefinition generate();
}
