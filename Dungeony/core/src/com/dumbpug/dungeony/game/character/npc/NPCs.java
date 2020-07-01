package com.dumbpug.dungeony.game.character.npc;

import com.dumbpug.dungeony.game.level.InteractiveLevel;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
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
    private LevelCollisionGrid levelCollisionGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the NPCs class.
     * @param npcs The list of NPCs.
     * @param levelCollisionGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public NPCs(ArrayList<? extends NPC> npcs, LevelCollisionGrid levelCollisionGrid, Renderables renderables) {
        this.npcs               = npcs;
        this.levelCollisionGrid = levelCollisionGrid;
        this.renderables        = renderables;

        // Add the initial list of NPCs to the level grid.
        this.levelCollisionGrid.add(npcs);

        // Add the initial list of NPCs to the renderables list.
        this.renderables.add(npcs);
    }

    /**
     * Update each of the NPCs sequentially.
     * @param level The interactive level.
     */
    public void update(InteractiveLevel level) {
        // Update each of the NPCs sequentially.
        for (NPC npc : this.npcs) {
            npc.update(level);
        }
    }
}
