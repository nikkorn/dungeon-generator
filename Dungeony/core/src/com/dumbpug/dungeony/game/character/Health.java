package com.dumbpug.dungeony.game.character;

/**
 * Represents character health.
 */
public class Health {
    /**
     * The base health slots that the character has.
     */
    private int healthSlots;
    /**
     * The current amount of health that a character has, out of all available health slots.
     */
    private int healthPoints;
    /**
     * The additional health slots that the character has.
     */
    private int additionalSlots = 0;
    /**
     * Whether the character is poisoned.
     */
    public boolean isPoisoned = false;
    /**
     * Whether the character is invincible.
     */
    public boolean isInvincible = false;

    /**
     * Creates a new instance of the Health class.
     * @param healthSlots The
     */
    public Health(int healthSlots) {
        this.healthSlots  = healthSlots;
        this.healthPoints = healthSlots;
    }

    /**
     * Gets the base health slots that the character has.
     * @return The base health slots that the character has.
     */
    public int getHealthSlots() {
        return healthSlots;
    }

    /**
     * Sets the base health slots that the character has.
     * @param healthSlots The base health slots that the character has.
     */
    public void setHealthSlots(int healthSlots) {
        this.healthSlots = healthSlots;
    }

    /**
     * Gets the current amount of health that a character has, out of all available health slots.
     * @return The current amount of health that a character has, out of all available health slots.
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the current amount of health that a character has, out of all available health slots.
     * @param healthPoints The current amount of health that a character has, out of all available health slots.
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Gets the number of additional health slots.
     * @return The number of additional health slots.
     */
    public int getAdditionalSlots() {
        return additionalSlots;
    }

    /**
     * Sets the number of additional health slots.
     * @param additionalSlots The number of additional health slots.
     */
    public void setAdditionalSlots(int additionalSlots) {
        this.additionalSlots = additionalSlots;
    }

    /**
     * Gets whether the character is poisoned.
     * @return Whether the character is poisoned.
     */
    public boolean isPoisoned() {
        return isPoisoned;
    }

    /**
     * Sets whether the character is poisoned.
     * @param poisoned Whether the character is poisoned.
     */
    public void setPoisoned(boolean poisoned) {
        isPoisoned = poisoned;
    }

    /**
     * Gets whether the character is invincible.
     * @return Whether the character is invincible.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Sets whether the character is invincible.
     * @param invincible Whether the character is invincible.
     */
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    /**
     * Gets whether the player is dead.
     * @return Whether the player is dead.
     */
    public boolean isDead() {
        return healthPoints == 0;
    }
}
