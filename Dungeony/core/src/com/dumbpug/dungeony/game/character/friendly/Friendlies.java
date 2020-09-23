package com.dumbpug.dungeony.game.character.friendly;

import com.dumbpug.dungeony.game.character.npc.NPCs;

import java.util.ArrayList;

/**
 * The list of in-level friendlies.
 */
public class Friendlies extends NPCs {
    /**
     * Creates an instance of the Friendlies class.
     * @param friendlies         The list of friendlies.
     * @param EnvironmentCollisionGrid The level grid.
     * @param renderables        The renderables list to keep updated with this list.
     */
    public Friendlies(ArrayList<Friendly> friendlies, EnvironmentCollisionGrid EnvironmentCollisionGrid, Renderables renderables) {
        super(friendlies, EnvironmentCollisionGrid, renderables);
    }
}
