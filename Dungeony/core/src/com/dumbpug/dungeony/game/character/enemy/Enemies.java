package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.engine.Environment;
import com.dumbpug.dungeony.game.character.npc.NPCs;
import java.util.ArrayList;

/**
 * The list of in-level enemies.
 */
public class Enemies extends NPCs {
    /**
     * Creates an instance of the Enemies class.
     * @param enemies The list of enemies.
     * @param environment The game environment.
     */
    public Enemies(ArrayList<Enemy> enemies, Environment environment) {
        super(enemies, environment);

        // Add the enemies to the game environment.
        environment.addEntities(enemies, "enemy");
    }
}
