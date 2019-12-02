package com.dumbpug.dungeony.characterselection;

import com.dumbpug.dungeony.game.character.PlayerIdentifier;
import com.dumbpug.dungeony.input.IPlayerInputProvider;

/**
 * Representation of a player picked at character selection.
 */
public class PlayerDetails {
    /**
     * The player id.
     */
    private PlayerIdentifier id;
    /**
     * The player input provider.
     */
    private IPlayerInputProvider inputProvider;

    // TODO Add players perks or customisations.

    /**
     * Creates a new instance of the PlayerDetails class.
     * @param id The player id.
     * @param inputProvider The player input provider.
     */
    public PlayerDetails(PlayerIdentifier id, IPlayerInputProvider inputProvider) {
        this.id            = id;
        this.inputProvider = inputProvider;
    }

    /**
     * Gets the player id.
     * @return The player id.
     */
    public PlayerIdentifier getId() {
        return id;
    }

    /**
     * Gets the player input provider.
     * @return The player input provider.
     */
    public IPlayerInputProvider getInputProvider() {
        return inputProvider;
    }
}
