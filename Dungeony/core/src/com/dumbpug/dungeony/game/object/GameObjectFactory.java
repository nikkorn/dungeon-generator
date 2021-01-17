package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.object.objects.Vendor;
import com.dumbpug.levelgeneration.IEntityProperties;

/**
 * Factory for creating game object instances.
 */
public class GameObjectFactory {
    /**
     * Create a GameObject instance of the given type.
     * @param type The game object type.
     * @param position The initial position of the object.
     * @param properties The entity properties.
     * @return A GameObject instance of the given type and position.
     */
    public static GameObject create(GameObjectType type, Position position, IEntityProperties properties) {
        switch (type) {
            case VENDOR:
                return new Vendor(position, properties);
            default:
                throw new RuntimeException("cannot create GameObject instance for unknown type: " + type);
        }
    }
}
