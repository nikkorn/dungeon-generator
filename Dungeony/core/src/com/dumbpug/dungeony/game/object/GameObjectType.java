package com.dumbpug.dungeony.game.object;

/**
 * Enumeration of game object types.
 */
public enum GameObjectType {
    PLAYER_SPAWN,
    CHEST,
    VENDOR;

    /**
     * Gets whether the string value is a valid enum value.
     * @param value The string value.
     * @return Whether the string value is a valid enum value.
     */
    public static boolean isValue(String value) {
        for (GameObjectType type : GameObjectType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
