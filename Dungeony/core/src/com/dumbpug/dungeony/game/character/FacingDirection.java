package com.dumbpug.dungeony.game.character;

/**
 * Enumeration of game character facing direction types.
 */
public enum FacingDirection {
    LEFT,
    RIGHT;

    /**
     * Gets the angle of the facing direction.
     * @return The angle of the facing direction.
     */
    public float getAngle() {
        switch(this) {
            case LEFT:
                return 270;
            case RIGHT:
                return 90;
            default:
                throw new RuntimeException("unknown FacingDirection enum value: " + this);
        }
    }

    /**
     * Gets a facing direction based on an angle.
     * @param angle
     * @return a facing direction based on an angle.
     */
    public static FacingDirection fromAngle(float angle) {
        return angle < 0 ? FacingDirection.LEFT : FacingDirection.RIGHT;
    }
}
