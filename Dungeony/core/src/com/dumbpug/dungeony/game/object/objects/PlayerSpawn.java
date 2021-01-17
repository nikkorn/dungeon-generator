package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.EntityCollisionFlag;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.levelgeneration.IEntityProperties;

/**
 * An object representing a player spawn.
 */
public class PlayerSpawn extends GameObject {
    /**
     * Creates a new instance of the PlayerSpawn class.
     * @param origin The initial origin of the PlayerSpawn.
     */
    public PlayerSpawn(Position origin, IEntityProperties properties) {
        super(origin, properties);
    }

    @Override
    public float getLengthX() {
        return Constants.LEVEL_TILE_SIZE * 0.5f;
    }

    @Override
    public float getLengthY() {
        return Constants.LEVEL_TILE_SIZE * 0.5f;
    }

    @Override
    public float getLengthZ() {
        return Constants.LEVEL_TILE_SIZE * 0.5f;
    }

    @Override
    public int getCollisionLayers() {
        return EntityCollisionFlag.OBJECT;
    }

    @Override
    public int getCollisionMask() {
        // Nothing should collide with a player spawn.
        return EntityCollisionFlag.NOTHING;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) { }

    @Override
    public GameObjectType getType() {
        return GameObjectType.PLAYER_SPAWN;
    }
}
