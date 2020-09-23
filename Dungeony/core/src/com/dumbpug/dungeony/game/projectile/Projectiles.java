package com.dumbpug.dungeony.game.projectile;

import com.dumbpug.dungeony.engine.Environment;
import java.util.ArrayList;

/**
 * The list of in-level projectiles.
 */
public class Projectiles {
    /**
     * The underlying list of projectiles.
     */
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    /**
     * The game environment.
     */
    private Environment environment;

    /**
     * Creates an instance of the Projectiles class.
     * @param environment The game environment.
     */
    public Projectiles(Environment environment) {
        this.environment = environment;
    }

    /**
     * Add a projectile to the list of in-game projectile.
     * @param projectile The projectile to add.
     */
    public void add(Projectile projectile) {
        // There is nothing to do if the game object is already in out list of existing projectiles.
        if (this.projectiles.contains(projectile)) {
            return;
        }

        // Add the projectile to the underlying list of projectiles.
        this.projectiles.add(projectile);

        // Add the projectile to the game environment.
        this.environment.addEntity(projectile, "projectile");
    }

    /**
     * Remove a projectile from the list of in-game projectile.
     * @param projectile The projectile to remove.
     */
    public void remove(Projectile projectile) {
        // There is nothing to do if the projectile is not already in out list of existing projectiles.
        if (!this.projectiles.contains(projectile)) {
            return;
        }

        // Remove the projectile from the underlying list of projectiles.
        this.projectiles.remove(projectile);

        // Remove the projectile from the game environment.
        this.environment.removeEntity(projectile);
    }

    /**
     * Get the collection of all projectiles.
     * @return The collection of all projectiles.
     */
    public ArrayList<Projectile> getAll() {
        return this.projectiles;
    }
}
