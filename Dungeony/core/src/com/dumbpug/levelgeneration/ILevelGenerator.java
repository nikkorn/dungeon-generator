package com.dumbpug.levelgeneration;

/**
 * Generates levels based on level resource files.
 */
public interface ILevelGenerator {
    /**
     * Generates a level definition.
     * @param file The name of the file to use.
     * @return A level definition.
     */
    LevelDefinition generate(String file);
}
