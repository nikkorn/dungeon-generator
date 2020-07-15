package com.dumbpug.dungeony.game.character.enemy;

/**
 * Enumeration of enemy NPC types.
 */
public enum EnemyType {
    FISHMAN;

    /**
     * Gets whether the string value is a valid enum value.
     * @param value The string value.
     * @return Whether the string value is a valid enum value.
     */
    public static boolean isValue(String value) {
        for (EnemyType type : EnemyType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
