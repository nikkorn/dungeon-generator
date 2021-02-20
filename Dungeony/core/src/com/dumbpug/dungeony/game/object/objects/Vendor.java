package com.dumbpug.dungeony.game.object.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dumbpug.dungeony.engine.Area;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.game.lights.SpotLight;
import com.dumbpug.dungeony.game.object.GameObject;
import com.dumbpug.dungeony.game.object.GameObjectType;
import com.dumbpug.dungeony.text.FontProvider;
import com.dumbpug.dungeony.text.FontSize;
import com.dumbpug.dungeony.text.FontType;
import com.badlogic.gdx.utils.Align;
import com.dumbpug.levelgeneration.IEntityProperties;

/**
 * A vending machine that items can be purchased from.
 */
public class Vendor extends GameObject {
    /**
     * The area of interaction on front of the vendor.
     */
    private Area areaOfInteraction;

    BitmapFont font;

    /**
     * Creates a new instance of the Vendor class.
     * @param origin The initial origin of the Vendor.
     */
    public Vendor(Position origin, IEntityProperties properties) {
        super(origin, properties);

        font = FontProvider.getFont(FontType.MAIN_FONT, FontSize.MEDIUM);
        font.setColor(Color.WHITE);
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
        return 24f;
    }

    @Override
    public float getLengthY() {
        return 12f;
    }

    @Override
    public float getLengthZ() {
        return 37f;
    }

    @Override
    public void onEnvironmentEntry(InteractiveEnvironment environment) {
        environment.addLight(new SpotLight(this, 1f, 0.3f, 0.3f));
    }

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

    /**
     * Render the renderable using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        font.draw(batch, "I'm a vendor bitch!", this.getX(), this.getY(), Gdx.graphics.getWidth(), Align.left, true);
    }

    @Override
    public GameObjectType getType() {
        return GameObjectType.VENDOR;
    }
}
