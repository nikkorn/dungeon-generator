package com.dumbpug.dungeony.game.character;

import java.util.Collection;
import java.util.HashMap;

/**
 * The list of in-game players.
 */
public class Players {
    /**
     * The map of player identifiers to players.
     */
    private HashMap<PlayerIdentifier, Player> players = new HashMap<PlayerIdentifier, Player>();

    /**
     * Add a player to the list of players.
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }

    /**
     * Gets the player with the given id.
     * @param id The player id.
     * @return The player with the given id.
     */
    public Player getPlayer(PlayerIdentifier id) {
        return this.players.get(id);
    }

    /**
     * Gets the collection of all players.
     * @return The collection of all players.
     */
    public Collection<Player> getAll() {
        return this.players.values();
    }
}
