package com.dumbpug.dungeony.game.item;

/**
 * Enumeration of item types.
 */
public enum ItemType {
    /**
     * Key Items
     */
    LEVEL_KEY,

    /**
     * Weapons
     */
    TRUSTY_PISTOL,

    /**
     * Ammo
     */
    PISTOL_AMMO_15;

    /**
     * Gets the name of the item.
     * @return The name of the item.
     */
    public String getName() {
        switch (this) {
            case LEVEL_KEY:
                return "Level Key";
            case TRUSTY_PISTOL:
                return "Trusty Pistol";
            case PISTOL_AMMO_15:
                return "Pistol Ammo";
            default:
                throw new RuntimeException("no name defined for item type' " + this);
        }
    }
}
