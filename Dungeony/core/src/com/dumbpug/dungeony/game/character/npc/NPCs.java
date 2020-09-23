package com.dumbpug.dungeony.game.character.npc;

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
     * The spatial grid to use in finding game entity collisions.
     */
    private EnvironmentCollisionGrid EnvironmentCollisionGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the NPCs class.
     * @param npcs The list of NPCs.
     * @param EnvironmentCollisionGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public NPCs(ArrayList<? extends NPC> npcs, EnvironmentCollisionGrid EnvironmentCollisionGrid, Renderables renderables) {
        this.npcs               = npcs;
        this.EnvironmentCollisionGrid = EnvironmentCollisionGrid;
        this.renderables        = renderables;

        // Add the initial list of NPCs to the level grid.
        this.EnvironmentCollisionGrid.add(npcs);

        // Add the initial list of NPCs to the renderables list.
        this.renderables.add(npcs);
    }

    /**
     * Update each of the NPCs sequentially.
     * @param level The interactive level.
     */
    public void update(InteractiveEnvironment level) {
        // Update each of the NPCs sequentially.
        for (NPC npc : this.npcs) {
            npc.update(level);
        }
    }
}
