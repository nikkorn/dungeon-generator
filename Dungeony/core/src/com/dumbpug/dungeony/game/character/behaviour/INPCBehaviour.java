package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;

/**
 * Represents behaviour for an NPC character.
 */
public interface INPCBehaviour<TNPC extends NPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param grid The level collision grid.
     */
    void tick (TNPC subject, LevelCollisionGrid grid);
}
