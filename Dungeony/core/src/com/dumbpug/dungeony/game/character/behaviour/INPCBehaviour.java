package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.level.InteractiveLevel;

/**
 * Represents behaviour for an NPC character.
 */
public interface INPCBehaviour<TNPC extends NPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param level The interactive level.
     */
    void tick (TNPC subject, InteractiveLevel level);
}
