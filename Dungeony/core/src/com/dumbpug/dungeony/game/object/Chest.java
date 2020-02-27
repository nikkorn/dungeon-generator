package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.Position;

public class Chest extends GameObject {
    /**
     * Creates a new instance of the Chest class.
     * @param origin The initial origin of the GameObject.
     */
    public Chest(Position origin) {
        super(origin);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.CHEST;
    }

    @Override
    public float getWidth() {
        // A chest should fill the tile that it is on horizontally.
        return Constants.GAME_TILE_SIZE;
    }

    @Override
    public float getHeight() {
        // A chest should fill the tile that it is on vertically.
        return Constants.GAME_TILE_SIZE;
    }
}
