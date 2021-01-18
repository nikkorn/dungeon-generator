package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;
import java.util.ArrayList;

/**
 * Very basic enemy NPC behaviour.
 * @param <TNPC> The type of NPC.
 */
public class BasicEnemyBehaviour<TNPC extends NPC> implements INPCBehaviour<TNPC> {
    /**
     * Tick the NPC behaviour.
     * @param subject The NPC.
     * @param environment The game environment.
     * @param delta The delta time.
     */
    public void tick (TNPC subject, InteractiveEnvironment environment, float delta) {
        // There is nothing to do if the enemy is dead.
        if (subject.getState() == GameCharacterState.DEAD) {
            return;
        }

        // Do nothing if the subject is sleeping.
        if (subject.getState() == GameCharacterState.SLEEPING) {
            return;
        }

        // Get all of teh player entities.
        ArrayList<Entity> players = environment.getEntitiesInGroup("player");

        // TODO Find the closest player entity.

        // TODO Is closest player entity within range? If not then set idle and return here.

        // Move the NPC north-east at their normal movement speed.
        // environment.moveByDirection(subject, Direction.NORTH_EAST, subject.getMovementSpeed(), delta);

        // Move towards the closest player.
        // subject.walkByAngle(environment, 25f, subject.getMovementSpeed(), delta);

        environment.moveTowards(subject, getClosestPlayerEntity(subject, environment), subject.getMovementSpeed(), delta);
    }

    /**
     * Get the closest player entity to the subject.
     * @param subject The NPC.
     * @param environment The game environment.
     * @return The closest player entity to the subject.
     */
    private Entity getClosestPlayerEntity(TNPC subject, InteractiveEnvironment environment) {
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
}
