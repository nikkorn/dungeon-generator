package com.dumbpug.dungeony.game.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dumbpug.dungeony.Constants;
import com.dumbpug.dungeony.audio.AudioProvider;
import com.dumbpug.dungeony.audio.SoundEffect;
import com.dumbpug.dungeony.engine.Entity;
import com.dumbpug.dungeony.engine.InteractiveEnvironment;
import com.dumbpug.dungeony.engine.Position;
import com.dumbpug.dungeony.engine.lighting.Light;
import com.dumbpug.dungeony.engine.utilities.GameMath;
import com.dumbpug.dungeony.game.lights.SmallSpotLight;
import com.dumbpug.dungeony.game.lights.SpotLight;
import com.dumbpug.dungeony.game.rendering.Animation;

/**
 * A weapon that uses ammunition.
 */
public abstract class AmmunitionWeapon extends Weapon {
    /**
     * The ammo level of the weapon.
     */
    private int ammo;
    /**
     * The muzzle flash sprite.
     */
    private Sprite muzzleFlashSprite;
    /**
     * The muzzle flash light.
     */
    private Light muzzleFlashLight;

    /**
     * Creates a new instance of the AmmunitionWeapon class.
     * @param quality The initial weapon quality.
     */
    public AmmunitionWeapon(WeaponQuality quality) {
        super(quality);

        // Create the muzzle flash sprite for this weapon.
        muzzleFlashSprite = new Sprite(new Texture("images/weapon/" + this.getWeaponType()  + "/MUZZLE_FLASH.png"));
        muzzleFlashSprite.setOrigin(0, muzzleFlashSprite.getRegionHeight() / 2f);

        // Create the muzzle flash light for this weapon and initially disable it.
        muzzleFlashLight = new SpotLight(0, 0, 0.6f, 0.6f, 0.6f);
        muzzleFlashLight.setEnabled(false);
    }

    /**
     * Gets the ammo level.
     * @return The ammo level.
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Sets the ammo level.
     * @param ammo The ammo level.
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * Attempt to use the weapon.
     * @param environment The interactive environment.
     * @param user The user of the weapon.
     * @param isTriggerJustPressed Whether the button pressed to use weapon has only just been pressed.
     * @param delta The application delta time.
     */
    @Override
    public void use(InteractiveEnvironment environment, Entity user, boolean isTriggerJustPressed, float delta) {
        // Add the muzzle flash light to the environment if it hasn't already been added.
        // TODO Only do once!
        environment.addLight(this.muzzleFlashLight);

        // If this weapon is not an automatic then it should only be used once per trigger press.
        if ((!this.isAutomatic()) && (!isTriggerJustPressed)) {
            return;
        }

        // Get the current time.
        long currentTime = System.currentTimeMillis();

        // Have we waited long enough since we last used this weapon?
        if (!this.isInCoolDown()) {
            // Do we have enough ammo to fire or do we have infinite ammo?
            if (ammo > 0) {
                // Use a unit of ammo.
                this.ammo--;

                // Reset the last fired time.
                this.lastUsed = currentTime;

                // We have successfully fired our weapon!
                this.onUse(environment, user, delta);

                // Play a noise.
                AudioProvider.getSoundEffect(SoundEffect.HANDGUN_FIRE).play();
            } else {
                // Reset the last fired time.
                this.lastUsed = currentTime;
            }
        }
    }

    /**
     * Reload the weapon.
     */
    public void reload() {
        // TODO Add weapon clips and reload.
    }

    /**
     * Render the weapon using the provided sprite batch.
     * @param batch The sprite batch to use in rendering the renderable.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Get the relevant animation for the weapon based on their current state.
        Animation animation = this.animations.get(this.getState());

        // Get the current animation frame for the animation.
        TextureRegion currentFrame = animation.getCurrentFrame();

        // Check whether we need to flip the weapon sprite based on its angle of aim.
        if (this.getAngleOfAim() < 0 || this.getAngleOfAim() > 180) {
            if (!currentFrame.isFlipY()) {
                currentFrame.flip(false, true);
            }
        } else {
            if (currentFrame.isFlipY()) {
               currentFrame.flip(false, true);
            }
        }

        // Draw the current animation frame.
        batch.draw(currentFrame, this.getPosition().getX(), this.getPosition().getY(), 0f, currentFrame.getRegionHeight() / 2f,
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), 1f, 1f, -(this.getAngleOfAim() - 90f));

        // Check whether we are in the duration after last firing where we would render a muzzle flash.
        boolean renderMuzzleFlash = this.lastUsed >= System.currentTimeMillis() - Constants.WEAPON_MUZZLE_FLASH_DURATION_MS;

        // Draw the muzzle flash animation if we are in the duration after last firing where we would render a muzzle flash.
        if (renderMuzzleFlash) {
            // Get the position we need to place our muzzle flash sprite at, which is at the end of the length of the weapon.
            Position muzzleFlashSpritePosition = GameMath.getPositionForAngle(this.getPosition().getX(), this.getPosition().getY(), this.getAngleOfAim(), this.getLength() * 0.8f);

            // Draw the current muzzle flash animation frame.
            muzzleFlashSprite.setPosition(muzzleFlashSpritePosition.getX(), muzzleFlashSpritePosition.getY());
            muzzleFlashSprite.setRotation(-(this.getAngleOfAim() - 90f));
            muzzleFlashSprite.draw(batch);

            // Get the position we need to place our muzzle flash light at, which is at the end of the length of the weapon.
            Position muzzleFlashLightPosition = GameMath.getPositionForAngle(this.getPosition().getX(),
                    this.getPosition().getY() + (currentFrame.getRegionHeight() * 0.7f), this.getAngleOfAim(), this.getLength() * 1.4f);

            // Update the position of the muzzle flash light.
            this.muzzleFlashLight.setPosition(muzzleFlashLightPosition.getX(), muzzleFlashLightPosition.getY());
        }

        // Enable or disable the muzzle flash light depending on whether we are in the duration after last firing where we would render a muzzle flash.
        this.muzzleFlashLight.setEnabled(renderMuzzleFlash);
    }

    /**
     * Gets whether the weapon is automatic fire.
     * @return Whether the weapon is automatic fire.
     */
    public abstract boolean isAutomatic();

    /**
     * Gets the effective spread of the weapon.
     * @return The effective spread of the weapon.
     */
    public abstract double getSpread();

    /**
     * Gets the length of the weapon.
     * @return The length of the weapon.
     */
    public abstract float getLength();
}
