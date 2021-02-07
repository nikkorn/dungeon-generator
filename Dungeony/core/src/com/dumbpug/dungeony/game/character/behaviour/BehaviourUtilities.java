package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.Area;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.game.character.npc.NPC;
import java.util.ArrayList;

/**
 * Utilities for use by behaviour classes.
 */
public class BehaviourUtilities {
    /**
     * Get the closest player entity to the subject.
     * @param subject The NPC.
     * @param environment The game environment.
     * @return The closest player entity to the subject.
     */
    public static <TNPC extends NPC> Entity getClosestPlayerEntity(TNPC subject, InteractiveEnvironment environment) {
        // Get all of the player entities.
        ArrayList<Entity> players = environment.getEntitiesInGroup("player");

        Entity closest = null;

        for (Entity currentPlayer : players) {
            if (closest == null) {
                closest = currentPlayer;
                continue;
            }

            // Get the distances between the subject and the closest/current player.
            float distanceToClosest       = subject.distanceTo(closest);
            float distanceToCurrentPlayer = subject.distanceTo(currentPlayer);

            if (distanceToCurrentPlayer < distanceToClosest) {
                closest = currentPlayer;
            }
        }

        return closest;
    }

    /**
     * Gets whether the subject has a lne of sight to the target entity.
     * @param subject The NPC.
     * @param target The target entity.
     * @param environment The game environment.
     * @return Whether the subject has a lne of sight to the target entity.
     */
    public static <TNPC extends NPC> boolean hasLineOfSightTo(TNPC subject, Entity target, InteractiveEnvironment environment) {
        // Initially, get the distance between thw two entities.
        float distanceBetweenEntities = subject.distanceTo(target);

        // Check whether the target is simply too far away for the subject to see.
        if (subject.getMaxVisibilityDistance() < distanceBetweenEntities) {
            return false;
        }

        float minX = Math.min(subject.getX(), target.getX());
        float maxX = Math.max(subject.getX(), target.getX());
        float minY = Math.min(subject.getY(), target.getY());
        float maxY = Math.max(subject.getY(), target.getY());

        // Get the area between the two entities.
        Area areaBetween = new Area(minX, minY, maxX - minX, maxY - minY);

        // Find any entities that overlap the area between the two entities. Any of these
        // could be blocking the line of sight between the subject and the target.
        for (Entity overlap : environment.getEntitiesInArea(areaBetween)) {
            if (!overlap.canObstructView()) {
                continue;
            }

            // TODO Check if line of sight is obsructed by overlap.
        }

        return true;

    }
}
