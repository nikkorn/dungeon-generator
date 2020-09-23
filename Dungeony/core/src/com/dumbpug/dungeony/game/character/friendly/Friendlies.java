package com.dumbpug.dungeony.game.character.friendly;

import com.dumbpug.dungeony.engine.Environment;
import com.dumbpug.dungeony.game.character.npc.NPCs;
import java.util.ArrayList;

/**
 * The list of in-level friendlies.
 */
public class Friendlies extends NPCs {
    /**
     * Creates an instance of the Friendlies class.
     * @param friendlies  The list of friendlies.
     * @param environment The game environment.
     */
    public Friendlies(ArrayList<Friendly> friendlies, Environment environment) {
        super(friendlies, environment);

        // Add the friendlies to the game environment.
        environment.addEntities(friendlies, "friendly");
    }
}
