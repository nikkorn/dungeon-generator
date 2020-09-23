package com.dumbpug.dungeony.game.tile;

import com.dumbpug.dungeony.engine.Position;

/**
 * Defines an entity spawn position provided by a tile.
 */
public class TileSpawn {
    /**
     * The origin of the spawn.
     * This is optional if the spawn is defined by a path.
     */
    private Position origin;
    /**
     * The location of the spawn.
     * If an origin is defined then this is the target location, otherwise it is just the spawn position.
     */
    private Position location;
    /**
     * Whether the spawn is defined by a path.
     */
    private boolean isPath;

    /**
     * Creates a new instance of the TileSpawn class with an origin and location defining a path.
     * @param location The spawn location.
     * @param origin The spawn path origin.
     */
    public TileSpawn(Position location, Position origin) {
        this.location = location;
        this.origin   = origin;
        this.isPath   = origin != null;
    }

    /**
     * Creates a new instance of the TileSpawn class with a location defining a spawn.
     * @param location The spawn location.
     */
    public TileSpawn(Position location) {
        this(location, null);
    }

    /**
     * Gets whether this tile spawn represents a path.
     * @return Whether this tile spawn represents a path.
     */
    public boolean isPath() {
        return isPath;
    }

    /**
     * Gets the location.
     * @return The location.
     */
    public Position getLocation() {
        return this.location;
    }

    /**
     * Gets the origin.
     * @return The origin.
     */
    public Position getOrigin() {
        return this.origin;
    }
}
