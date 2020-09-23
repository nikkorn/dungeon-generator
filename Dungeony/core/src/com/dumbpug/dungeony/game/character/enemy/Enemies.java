package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.game.character.npc.NPCs;

import java.util.ArrayList;

/**
 * The list of in-level enemies.
 */
public class Enemies extends NPCs {
    /**
     * Creates an instance of the Enemies class.
     * @param enemies            The list of enemies.
     * @param EnvironmentCollisionGrid The level grid.
     * @param renderables        The renderables list to keep updated with this list.
     */
    public Enemies(ArrayList<Enemy> enemies, EnvironmentCollisionGrid EnvironmentCollisionGrid, Renderables renderables) {
        super(enemies, EnvironmentCollisionGrid, renderables);
    }
}
