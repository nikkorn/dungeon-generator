package com.dumbpug.dungeony.input;

/**
 * Provider for player keyboard/controller input.
 */
public interface IPlayerInputProvider {
    /**
     * Gets the movement x axis value as a number between -1 and 1.
     * @return The movement x axis value as a number between -1 and 1.
     */
    float getMovementAxisX();

    /**
     * Gets the movement y axis value as a number between -1 and 1.
     * @return The movement y axis value as a number between -1 and 1.
     */
    float getMovementAxisY();

    /**
     * Gets the aim x axis value as a number between -1 and 1.
     * @return The aim x axis value as a number between -1 and 1.
     */
    float getAimAxisX();

    /**
     * Gets the aim y axis value as a number between -1 and 1.
     * @return The aim y axis value as a number between -1 and 1.
     */
    float getAimAxisY();

    /**
     * Gets whether the specified control is currently pressed.
     * @param control The control.
     * @return Whether the specified control is currently pressed.
     */
    boolean isControlPressed(Control control);

    /**
     * Gets whether the specified control has just been pressed.
     * @param control The control.
     * @return Whether the specified control has just been pressed.
     */
    boolean isControlJustPressed(Control control);

    /**
     * Reset the input.
     */
    void reset();
}
