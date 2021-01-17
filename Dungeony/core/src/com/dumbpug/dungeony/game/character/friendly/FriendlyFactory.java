package com.dumbpug.dungeony.game.character.friendly;

import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.friendly.friendlies.BlueJoe;
import com.dumbpug.levelgeneration.IEntityProperties;

/**
 * Factory for creating friendly NPC instances.
 */
public class FriendlyFactory {
    /**
     * Create an Friendly instance of the given type.
     * @param type The friendly type.
     * @param position The initial position of the friendly.
     * @param properties The entity properties.
     * @return An Friendly instance of the given type and position.
     */
    public static Friendly create(FriendlyType type, Position position, IEntityProperties properties) {
        switch (type) {
            case BLUEJOE:
                return new BlueJoe(position, properties);
            default:
                throw new RuntimeException("cannot create Friendly instance for unknown type: " + type);
        }
    }
}
