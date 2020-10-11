package com.dumbpug.dungeony.game.character.player;

import com.dumbpug.dungeony.characterselection.PlayerDetails;
import com.dumbpug.dungeony.engine.Environment;
import com.dumbpug.dungeony.game.tile.TileSpawn;
import com.dumbpug.dungeony.game.weapon.WeaponQuality;
import com.dumbpug.dungeony.game.weapon.handgun.Pistol;
import java.util.ArrayList;
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
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates a new instance of the Players class.
     * @param playerDetails The list of player details.
     * @param environment The game environment.
     * @param spawns The list of available spawn positions.
     */
    public Players(ArrayList<PlayerDetails> playerDetails, Environment environment, ArrayList<TileSpawn> spawns) {
        this.environment = environment;

        // Check that there are enough spawn positions for our players.
        if (playerDetails.size() > spawns.size()) {
            throw new RuntimeException("not enough spawns for players");
        }

        // Create an in-level Player instance for each player, giving them each an initial spawn position.
        for (PlayerDetails playerDetail : playerDetails) {
            // TODO Eventually handle the fact that a tile spawn can represent a spawn path to follow before the level begins.

            // Create a new player instance based on the player details and assign them an initial spawn.
            Player player = new Player(playerDetail, spawns.get(playerDetails.indexOf(playerDetail)).getLocation());

            // TODO: Remove this weapon test.
            player.setWeapon(new Pistol(WeaponQuality.AVERAGE));

            // Add the player to the game environment.
            this.environment.addEntity(player, "player");

            // Add the player to our players map.
            this.players.put(playerDetail.getId(), player);
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
}
