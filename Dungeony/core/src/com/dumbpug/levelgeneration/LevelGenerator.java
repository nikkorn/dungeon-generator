package com.dumbpug.levelgeneration;

/**
 * Generates levels based on level resource files.
 */
public class LevelGenerator implements ILevelGenerator {
    /**
     * The level resources path.
     */
    private String levelResourcesPath;

    /**
     * Creates a new instance of the LevelResources class.
     * @param levelResourcesPath The path to level resources.
     */
    public LevelGenerator(String levelResourcesPath) {
        this.levelResourcesPath = levelResourcesPath;
    }

    /**
     * Generates a level definition.
     * @param file The name of the file to use.
     * @return A level definition.
     */
    public LevelDefinition generate(String file) {
        // TODO Do the thing!.
        // TODO Substitute all tile types withe either WALL or EMPTY, more specific tile types will become entities.
        return null;
    }
}
