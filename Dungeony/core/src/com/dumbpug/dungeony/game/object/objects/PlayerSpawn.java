package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;

/**
 * An object representing a player spawn.
 */
public class PlayerSpawn extends GameObject {
    /**
     * Creates a new instance of the PlayerSpawn class.
     * @param origin The initial origin of the PlayerSpawn.
     */
    public PlayerSpawn(Position origin) {
        super(origin);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.PLAYER_SPAWN;
    }

    @Override
    public float getWidth() {
        return Constants.GAME_TILE_SIZE * 0.5f;
    }

    @Override
    public float getHeight() {
        return Constants.GAME_TILE_SIZE * 0.5f;
    }

    @Override
    public int getCollisionFlag() {
        return EntityCollisionFlag.OBJECT;
    }

    @Override
    public int getCollisionMask() {
        // Nothing should collide with a player spawn.
        return EntityCollisionFlag.NOTHING;
    }
}
