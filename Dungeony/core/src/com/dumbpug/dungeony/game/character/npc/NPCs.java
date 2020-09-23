package com.dumbpug.dungeony.game.character.npc;

import com.dumbpug.dungeony.engine.Environment;
import java.util.ArrayList;

/**
 * The list of in-level NPCs.
 */
public class NPCs {
    /**
     * The underlying list of NPCs.
     */
    private ArrayList<? extends NPC> npcs;
    /**
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates an instance of the NPCs class.
     * @param npcs The list of NPCs.
     * @param environment The game environment.
     */
    public NPCs(ArrayList<? extends NPC> npcs, Environment environment) {
        this.npcs        = npcs;
        this.environment = environment;
    }
}
