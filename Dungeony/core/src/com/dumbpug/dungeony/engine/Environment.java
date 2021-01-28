package com.dumbpug.dungeony.engine;

import com.dumbpug.dungeony.engine.audio.IAudioPlayer;
import com.dumbpug.dungeony.engine.dialog.Dialogs;
import com.dumbpug.dungeony.engine.lighting.Lights;
import com.dumbpug.dungeony.engine.rendering.IRenderWindow;
import com.dumbpug.dungeony.engine.rendering.Renderables;

/**
 * Represents a game environment.
 * @param <TRenderContext> The render context.
 */
public abstract class Environment<TRenderContext> {
    /**
     * The environment configuration.
     */
    private EnvironmentConfiguration configuration;
    /**
     * The spatial grid to use in finding entity collisions.
     */
    private EnvironmentCollisionGrid collisionGrid;
    /**
     * The renderables renderables collection which will handle entity render priority.
     */
    private Renderables<TRenderContext> renderables;
    /**
     * The collection of entities in the environment.
     */
    private Entities<TRenderContext> entities;
    /**
     * The collection of lights in the environment.
     */
    private Lights<TRenderContext> lights;
    /**
     * The collection of dialogs in the environment.
     */
    private Dialogs<TRenderContext> dialogs;
    /**
     * The environment interactivity layer that is available to entities during updates.
     */
    private InteractiveEnvironment interactiveEnvironment;

    /**
     * Creates a new instance of the Environment class.
     * @param configuration The environment configuration.
     * @param camera The environment camera.
     * @param audioPlayer The audio player.
     */
    public Environment(EnvironmentConfiguration configuration, IEnvironmentCamera camera, IAudioPlayer audioPlayer) {
        // Keep a reference to the environment configuration.
        this.configuration = configuration;

        // Create the spatial grid to use in finding entity collisions.
        this.collisionGrid = new EnvironmentCollisionGrid(configuration.gridCellSize);

        // Create the renderables collection which will handle entity render priority.
        this.renderables = new Renderables<TRenderContext>();

        // Create the lights collection.
        this.lights = new Lights();

        // Create the dialogs collection.
        this.dialogs = new Dialogs();

        // Create the environment interactivity layer that is available to entities during updates.
        this.interactiveEnvironment = new InteractiveEnvironment(this, camera, audioPlayer);

        // Create the entities collection.
        this.entities = new Entities(this.collisionGrid, this.renderables, this.interactiveEnvironment);
    }

    /**
     * Gets the collection of entities in the environment.
     * @return The collection of entities in the environment.
     */
    public Entities<TRenderContext> getEntities() {
        return entities;
    }

    /**
     * Gets the collection of lights in the environment.
     * @return The collection of lights in the environment.
     */
    public Lights<TRenderContext> getLights() {
        return lights;
    }

    /**
     * Gets the collection of dialogs in the environment.
     * @return The collection of dialogs in the environment.
     */
    public Dialogs<TRenderContext> getDialogs() {
        return dialogs;
    }

    /**
     * Gets the spatial grid to use in finding entity collisions.
     * @return The spatial grid to use in finding entity collisions.
     */
    public EnvironmentCollisionGrid getGrid() {
        return this.collisionGrid;
    }

    /**
     * Gets the environment interactivity layer that is available to entities during updates.
     * @return The environment interactivity layer that is available to entities during updates.
     */
    public InteractiveEnvironment getInteractivityLayer() {
        return this.interactiveEnvironment;
    }

    /**
     * Update the environment and any contained entities.
     * @param delta The delta time.
     */
    public void update(float delta) {
        this.entities.update(delta);

        // TODO: Add update for dialogs to handle adding or removing of interacting entities.
    }

    /**
     * Render the renderable entities in the environment that lie within the given render window.
     * @param context The render context.
     * @param window The render window.
     */
    public void render(TRenderContext context, IRenderWindow window) {
        onBeforeEntitiesRender(context);

        this.renderables.render(context, window);

        onAfterEntitiesRender(context);
        onBeforeLightsRender(context);

        this.lights.render(context);

        onAfterLightsRender(context);
        onBeforeDialogsRender(context);

        this.dialogs.render(context);

        onAfterDialogsRender(context);
    }

    /**
     * Render all of the renderable entities in the environment.
     * @param context The render context.
     */
    public void render(TRenderContext context) {
        this.render(context, null);
    }

    public abstract void onBeforeEntitiesRender(TRenderContext context);

    public abstract void onAfterEntitiesRender(TRenderContext context);

    public abstract void onBeforeLightsRender(TRenderContext context);

    public abstract void onAfterLightsRender(TRenderContext context);

    public abstract void onBeforeDialogsRender(TRenderContext context);

    public abstract void onAfterDialogsRender(TRenderContext context);
}
