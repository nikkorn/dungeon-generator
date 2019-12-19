package com.dumbpug.dungeony.game.character;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.game.Position;
import com.dumbpug.dungeony.game.level.LevelGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
import java.util.ArrayList;
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
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelGrid levelGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates a new instance of the Players class.
     * @param playerDetails The list of player details.
     * @param levelGrid The level grid
     * @param renderables The renderables list to keep updated with this list.
     * @param spawns The list of available spawn positions.
     */
    public Players(ArrayList<PlayerDetails> playerDetails, LevelGrid levelGrid, Renderables renderables, ArrayList<Position> spawns) {
        this.levelGrid   = levelGrid;
        this.renderables = renderables;

        // Check that there are enough spawn positions for our players.
        if (playerDetails.size() > spawns.size()) {
            throw new RuntimeException("not enough spawns for players");
        }

        // Create an in-level Player instance for each player, giving them each an initial spawn position.
        for (PlayerDetails playerDetail : playerDetails) {
            // Create a new player instance based on the player details.
            Player player = new Player(playerDetail.getId(), spawns.get(playerDetails.indexOf(playerDetail)));

            // Add the new player to the level grid.
            this.levelGrid.add(player);

            // Add the new player to the  to the renderables list.
            this.renderables.add(player);
        }
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
