package com.dumbpug.dungeony.game.character.enemy.enemies;

import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.audio.AudioProvider;
import com.dumbpug.dungeony.audio.SoundEffect;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.behaviour.BasicEnemyBehaviour;
import com.dumbpug.dungeony.game.character.enemy.Enemy;
import com.dumbpug.dungeony.game.character.enemy.EnemyType;
import com.dumbpug.dungeony.game.lights.SpotLight;

/**
 * A standard grunt enemy.
 */
public class Grunt extends Enemy {
    /**
     * Creates a new instance of the Grunt class.
     * @param origin The initial origin of the Grunt.
     */
    public Grunt(Position origin) {
        super(origin);

        // Give the Grunt some basic enemy behaviour.
        this.setBehaviour(new BasicEnemyBehaviour());
    }

    @Override
    public float getLengthX() {
        return 18f;
    }

    @Override
    public float getLengthY() {
        return 10f;
    }

    @Override
    public float getLengthZ() {
        return 24f;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(new SpotLight(this, 1f, 0.3f, 0.3f));
    }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public float getMovementSpeed() {
        // A grunt can move at 80% of the default speed of the player.
        return Constants.PLAYER_MOVEMENT_PS * 0.8f;
    }

    @Override
    public void onDamageTaken(InteractiveEnvironment environment, float delta, int points) {
        // Do a groan!
        AudioProvider.getSoundEffect(SoundEffect.GRUNT_GROAN).play();
    }

    @Override
    public void onHealthDepleted(InteractiveEnvironment environment, float delta) {
        // Do a groan of death!
        AudioProvider.getSoundEffect(SoundEffect.GRUNT_DEAD).play();

        // Set the character state to be DEAD.
        this.setState(GameCharacterState.DEAD);
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.GRUNT;
    }
}