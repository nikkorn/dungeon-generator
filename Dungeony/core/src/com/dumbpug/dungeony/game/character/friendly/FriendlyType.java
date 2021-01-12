package com.dumbpug.dungeony.game.character.friendly;

/**
 * Enumeration for friendly NPC types.
 */
public enum FriendlyType {
    BLUEJOE;

    /**
     * Gets whether the string value is a valid enum value.
     * @param value The string value.
     * @return Whether the string value is a valid enum value.
     */
    public static boolean isValue(String value) {
        for (FriendlyType type : FriendlyType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
