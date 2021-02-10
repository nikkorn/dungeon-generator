package com.dumbpug.dungeony.game.character.behaviour;

import com.dumbpug.dungeony.engine.Area;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.character.GameCharacterState;
import com.dumbpug.dungeony.game.character.npc.NPC;
import com.dumbpug.dungeony.game.weapon.AmmunitionWeapon;
import com.dumbpug.dungeony.game.weapon.MeleeWeapon;
import com.dumbpug.dungeony.game.weapon.Weapon;
import java.util.ArrayList;
import java.util.Random;

/**
 * Base class for NPC behaviours.
 */
public abstract class NPCBehaviour<TNPC extends NPC> {
    /**
     * The behaviour subject.
     */
    protected TNPC subject = null;
    /**
     * The subject environment.
     */
    protected InteractiveEnvironment environment = null;
    /**
     * The current delta time.
     */
    protected float delta = 0f;
    /**
     * The RNG to use in generating random attack cool-downs.
     */
    private Random rng = new Random();
    /**
     * The time in millis from which the NPC can carry out another attack.
     */
    private long canAttackFrom = 0l;

    /**
     * Sets up the behaviour.
     * @param subject The behaviour subject.
     * @param environment The subject environment.
     * @param delta The current delta time.
     */
    public void setUp(TNPC subject, InteractiveEnvironment environment, float delta) {
        this.subject     = subject;
        this.environment = environment;
        this.delta       = delta;
    }

    /**
     * Gets the current subject character state.
     * @return The current subject character state.
     */
    public GameCharacterState getState() {
        return subject.getState();
    }

    /**
     * Sets the current subject character state.
     * @param state The current subject character state.
     */
    public void setState(GameCharacterState state) {
        subject.setState(state);
    }

    /**
     * Gets whether the current subject character state matches the one specified.
     * @param state The state.
     * @return Whether the current subject character state matches the one specified.
     */
    public boolean inState(GameCharacterState state) {
        return getState() == state;
    }

    /**
     * Gets the current subject character weapon.
     * @return The current subject character weapon.
     */
    public Weapon getWeapon() {
        return subject.getWeapon();
    }

    /**
     * Gets the distance between the current subject character and the target entity.
     * @param target The target entity.
     * @return The distance between the current subject character and the target entity.
     */
    public float distanceTo(Entity target) {
        return subject.distanceTo(target);
    }

    /**
     * Gets the angle between the current subject character and the target entity.
     * @param target The target entity.
     * @return The angle between the current subject character and the target entity.
     */
    public float angleTo(Entity target) {
        return subject.angleTo(target);
    }

    /**
     * Gets the angle of the current subject character direction of view.
     * @return The angle of the current subject character direction of view.
     */
    public Float getAngleOfView() {
        return subject.getAngleOfView();
    }

    /**
     * Sets the angle of the current subject character direction of view.
     * @param angleOfView The angle of the current subject character direction of view.
     */
    public void setAngleOfView(Float angleOfView) {
        subject.setAngleOfView(angleOfView);
    }

    /**
     * Called just before 'tick'.
     */
    public void onBeforeTick() {
        // ...
    }

    /**
     * Called just after 'tick'.
     */
    public void onAfterTick() {
        // ...
    }

    /**
     * Get the closest player entity to the subject.
     * @return The closest player entity to the subject.
     */
    protected Entity getClosestPlayerEntity() {
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
     * Gets whether the subject can see the target entity.
     * @param target The target entity.
     * @return Whether the subject can see the target entity.
     */
    protected boolean canSee(Entity target) {




        // TODO Remove and complete proper check below. For now this just checks whether the target is in the subject view range.
        if (1 == 1)
            return distanceTo(target) < subject.getMaxVisibilityDistance();




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

    /**
     * Gets whether the specified weapon can be used to attack the target entity.
     * @param target The target entity.
     * @return Whether the specified weapon can be used to attack the target entity.
     */
    protected boolean canAttack(Entity target) {
        // Get the weapon of the subject.
        Weapon weapon = subject.getWeapon();

        // We 100% cant attack if we have no weapon to attack with.
        if (weapon == null) {
            return false;
        }

        // No weapon can be used if it is currently in cool-down.
        if (weapon.isInCoolDown()) {
            return false;
        }

        // The NPC also has it's own attack cool-down.
        if (System.currentTimeMillis() < this.canAttackFrom) {
            return false;
        }

        // We cannot use a weapon when the target is outside the effective range.
        if (weapon.getRange() < GameMath.getLength(weapon.getPosition().getX(), weapon.getPosition().getY(), target.getOrigin().getX(), target.getOrigin().getY())) {
            return false;
        }

        switch (weapon.getWeaponCategory()) {
            case HANDGUN:
            case RIFLE:
            case AUTO_RIFLE:
            case SHOTGUN:
            case ROCKET:
                // Ammunition weapons can be used if there is enough ammo to do so.
                return ((AmmunitionWeapon)weapon).getAmmo() > 0;

            default:
                return true;
        }
    }

    /**
     * Attack using the currently equipped weapon and current angle of view.
     */
    protected void attack() {
        // Get the weapon of the subject.
        Weapon weapon = subject.getWeapon();

        // The subject cannot attack without a weapon.
        if (weapon == null) {
            return;
        }

        // Attack using the weapon!
        weapon.use(environment, subject, true, delta);

        // Keep track of when the subject can next attack.
        this.canAttackFrom = System.currentTimeMillis() + getAttackCoolDown();
    }

    /**
     * Attack using the currently equipped weapon and the angle of view to the target.
     * @param target The target.
     */
    protected void attack(Entity target) {
        // Get the weapon of the subject.
        Weapon weapon = subject.getWeapon();

        // The subject cannot attack without a weapon.
        if (weapon == null) {
            return;
        }

        // Aim at the target before attacking.
        setAngleOfView(angleTo(target));

        // Attack using the weapon!
        attack();
    }

    /**
     * Walk the subject, following the specified angle of movement.
     * @param angle The angle of movement.
     */
    protected void walk(float angle) {
        subject.walkByAngle(environment, angle, subject.getMovementSpeed(), delta);
    }

    /**
     * Walk the subject towards the specified target.
     * @param target The target.
     */
    protected void walkTowards(Entity target) {
        walk(subject.angleTo(target));
    }

    /**
     * Gets the attack cool-down to use based on the the equipped subject weapon.
     * This differs from the actual weapon cool-down as NPC attacking isn't trigger based.
     * @return The cool-down to use for the equipped subject weapon.
     */
    private long getAttackCoolDown() {
        long coolDownMin = 0;
        long coolDownMax = 0;

        switch (subject.getWeapon().getWeaponCategory()) {
            case MELEE:
            case BAT:
                coolDownMin = 2000l;
                coolDownMax = 3000l;
                break;

            case HANDGUN:
            case RIFLE:
                coolDownMin = 600l;
                coolDownMax = 2000l;
                break;

            case SHOTGUN:
                coolDownMin = 2500l;
                coolDownMax = 3500l;
                break;

            case ROCKET:
                coolDownMin = 4000l;
                coolDownMax = 8000l;
                break;

            case AUTO_RIFLE:
                coolDownMin = 0l;
                coolDownMax = 0l;
                break;

            default:
                coolDownMin = 1000l;
                coolDownMax = 1000l;
                break;
        }

        return (long)(coolDownMin + this.rng.nextInt((int)coolDownMax));
    }

    /**
     * Tick the NPC behaviour.
     */
    public abstract void onTick();
}
