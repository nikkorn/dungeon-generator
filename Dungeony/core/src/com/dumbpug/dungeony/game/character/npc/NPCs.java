package com.dumbpug.dungeony.game.character.npc;

import com.dumbpug.dungeony.engine.Environment;
import java.util.ArrayList;

/**
 * The list of in-level NPCs.
 */
public class NPCs<TNPC extends NPC> {
    /**
     * The underlying list of NPCs.
     */
    private ArrayList<TNPC> npcs;
    /**
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates an instance of the NPCs class.
     * @param npcs The list of NPCs.
     * @param environment The game environment.
     */
    public NPCs(ArrayList<TNPC> npcs, Environment environment) {
        this.npcs        = npcs;
        this.environment = environment;
    }

    public ArrayList<TNPC> get() {
        return this.npcs;
    }
}
