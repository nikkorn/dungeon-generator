package com.dumbpug.dungeony.characterselection;

import com.dumbpug.dungeony.game.character.PlayerIdentifier;

/**
 * Representation of a player picked at character selection.
 */
public class PlayerDetails {
    /**
     * The player id.
     */
    private PlayerIdentifier id;

    // TODO Add the input handler for this player.
    // TODO Add players perks or customisations.

    /**
     * Creates a new instance of the PlayerDetails class.
     * @param id The player id.
     */
    public PlayerDetails(PlayerIdentifier id) {
        this.id = id;
    }
}
