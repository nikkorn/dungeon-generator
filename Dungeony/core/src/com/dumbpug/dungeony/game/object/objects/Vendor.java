package com.dumbpug.dungeony.game.object.objects;

import com.dumbpug.dungeony.engine.Area;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;

/**
 * A vending machine that items can be purchased from.
 */
public class Vendor extends GameObject {
    /**
     * The area of interaction on front of the vendor.
     */
    private Area areaOfInteraction;

    /**
     * Creates a new instance of the Vendor class.
     * @param origin The initial origin of the Vendor.
     */
    public Vendor(Position origin) {
        super(origin);
    }

    @Override
    public void onPositioned() {
        // Create the area of interaction for the vendor, this will be a small area directly in front of the machine.
        areaOfInteraction = new Area(
                new Position(this.getOrigin().getX(), this.getOrigin().getY() - getLengthY()),
                this.getLengthX() * 0.3f,
                this.getLengthY() * 0.3f
        );
    }

    @Override
    public float getLengthX() {
        return 20f;
    }

    @Override
    public float getLengthY() {
        return 10f;
    }

    @Override
    public float getLengthZ() {
        return 31f;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) { }

    @Override
    public void onEnvironmentExit(InteractiveEnvironment environment) { }

    @Override
    public void update(InteractiveEnvironment environment, float delta) {
        // TODO Find all players that are in front of the machine.
        for (Entity entity : environment.getEntitiesInArea(this.areaOfInteraction)) {
            // Get the group that the current entity is in.
            String group = environment.getEntityGroup(entity);

            if (group != null && group.equalsIgnoreCase("player")) {
                System.out.println(entity.getClass().getName());
            }
        }
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.VENDOR;
    }
}
