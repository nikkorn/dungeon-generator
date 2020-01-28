package com.dumbpug.levelgeneration;

import java.util.ArrayList;

/**
 * A level generator for a simple static level.
 */
public class SimpleLevelGenerator implements ILevelGenerator {
    /**
     * Generates a level definition.
     * @param file The name of the file to use.
     * @return A level definition.
     */
    @Override
    public LevelDefinition generate(String file) {
        // Create a few tile definitions.
        ArrayList<TileDefinition> tiles = new ArrayList<TileDefinition>() {{
            add(new TileDefinition("WALL",0,0));
            add(new TileDefinition("WALL",1,0));
            add(new TileDefinition("WALL",2,0));
            add(new TileDefinition("WALL",3,0));
            add(new TileDefinition("WALL",0,1));
            add(new TileDefinition("EMPTY",1,1));
            add(new TileDefinition("EMPTY",2,1));
            add(new TileDefinition("WALL",3,1));
            add(new TileDefinition("WALL",0,2));
            add(new TileDefinition("EMPTY",1,2));
            add(new TileDefinition("EMPTY",2,2));
            add(new TileDefinition("WALL",3,2));
            add(new TileDefinition("WALL",0,3));
            add(new TileDefinition("WALL",1,3));
            add(new TileDefinition("WALL",2,3));
            add(new TileDefinition("WALL",3,3));
        }};

        // Return the level definition.
        return new LevelDefinition("Simple", tiles);
    }
}
