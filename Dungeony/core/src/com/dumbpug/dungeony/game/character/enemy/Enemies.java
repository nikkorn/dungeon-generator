package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.game.character.npc.NPCs;
import com.dumbpug.dungeony.game.level.LevelCollisionGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
import java.util.ArrayList;

/**
 * The list of in-level enemies.
 */
public class Enemies extends NPCs {
    /**
     * Creates an instance of the Enemies class.
     * @param enemies            The list of enemies.
     * @param levelCollisionGrid The level grid.
     * @param renderables        The renderables list to keep updated with this list.
     */
    public Enemies(ArrayList<Enemy> enemies, LevelCollisionGrid levelCollisionGrid, Renderables renderables) {
        super(enemies, levelCollisionGrid, renderables);
    }
}
