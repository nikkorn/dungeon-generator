package com.dumbpug.dungeony.game.character.enemy.enemies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.FacingDirection;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.behaviour.BasicEnemyBehaviour;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.lights.SpotLight;

/**
 * A basic fishman enemy.
 */
public class Fishman extends Enemy {
    /**
     * Creates a new instance of the Fishman class.
     * @param origin The initial origin of the Fishman.
     */
    public Fishman(Position origin) {
        super(origin);

        // Give the Fishman some basic enemy behaviour.
        this.setBehaviour(new BasicEnemyBehaviour());
    }

    @Override
    public float getLengthX() {
        // Fishman is the same width as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthY() {
        // Fishman is the same height as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public float getLengthZ() {
        // Fishman is the same height as the player.
        return Constants.PLAYER_SIZE;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(new SpotLight(this, 1f, 0.3f, 0.3f));
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public float getMovementSpeed() {
        // Fishman can move at 80% of the default speed of the player.
        return Constants.PLAYER_MOVEMENT_PS * 0.8f;
    }

    @Override
    public void onDamageTaken(InteractiveEnvironment environment, float delta, int points) {

    }

    @Override
    public void onHealthDepleted(InteractiveEnvironment environment, float delta) {
        // TODO: Set the character state to be DEAD.
        this.setState(this.getFacingDirection() == FacingDirection.LEFT ? GameCharacterState.DEAD_LEFT : GameCharacterState.DEAD_RIGHT);
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.FISHMAN;
    }
}
