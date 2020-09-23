package com.dumbpug.dungeony.game.character.friendly;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.friendly.friendlies.BlueJoe;
import org.json.JSONObject;

/**
 * Factory for creating friendly NPC instances.
 */
public class FriendlyFactory {
    /**
     * Create an Friendly instance of the given type.
     * @param type The friendly type.
     * @param position The initial position of the friendly.
     * @param details A JSON object containing any additional details about the friendly.
     * @return An Friendly instance of the given type and position.
     */
    public static Friendly create(FriendlyType type, Position position, JSONObject details) {
        switch (type) {
            case BLUEJOE:
                return new BlueJoe(position);
            default:
                throw new RuntimeException("cannot create Friendly instance for unknown type: " + type);
        }
    }
}
