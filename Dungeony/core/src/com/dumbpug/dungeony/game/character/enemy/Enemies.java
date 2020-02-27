package com.dumbpug.dungeony.game.character.enemy;

import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.level.LevelGrid;
import com.dumbpug.dungeony.game.rendering.Renderables;
import java.util.ArrayList;

/**
 * The list of in-level enemies.
 */
public class Enemies {
    /**
     * The underlying list of enemies.
     */
    private ArrayList<com.dumbpug.dungeony.game.character.enemy.Enemy> enemies;
    /**
     * The spatial grid to use in finding game entity collisions.
     */
    private LevelGrid levelGrid;
    /**
     * The renderables list to keep updated with this list.
     */
    private Renderables renderables;

    /**
     * Creates an instance of the Enemies class.
     * @param enemies The list of enemies.
     * @param levelGrid The level grid.
     * @param renderables The renderables list to keep updated with this list.
     */
    public Enemies(ArrayList<com.dumbpug.dungeony.game.character.enemy.Enemy> enemies, LevelGrid levelGrid, Renderables renderables) {
        this.enemies     = enemies;
        this.levelGrid   = levelGrid;
        this.renderables = renderables;

        // Add the initial list of enemies to the level grid.
        this.levelGrid.add(enemies);

        // Add the initial list of enemies to the renderables list.
        this.renderables.add(enemies);
    }

    /**
     * Update each of the enemies sequentially.
     * @param grid The level grid used to handle enemy movement during an update.
     */
    public void update(LevelGrid grid) {
        // Update each of the players sequentially.
        for (Enemy enemy : this.enemies) {
            enemy.update(grid);
        }
    }
}
