package com.dumbpug.dungeony.game.object;

import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.object.objects.Vendor;

import org.json.JSONObject;

/**
 * Factory for creating Level instances.
 */
public class GameObjectFactory {
    /**
     * Create a GameObject instance of the given type.
     * @param type The game object type.
     * @param position The initial position of the object.
     * @param details A JSON object containing any additional details about the object.
     * @return A GameObject instance of the given type and position.
     */
    public static GameObject create(GameObjectType type, Position position, JSONObject details) {
        switch (type) {
            case VENDOR:
                return new Vendor(position);
            default:
                throw new RuntimeException("cannot create GameObject instance for unknown type: " + type);
        }
    }
}
