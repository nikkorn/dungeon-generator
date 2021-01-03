package com.dumbpug.dungeony.engine.dialog;

import com.dumbpug.dungeony.engine.Entity;

/**
 * An environment dialog.
 * @param <TRenderContext> The render context.
 */
public abstract class Dialog<TRenderContext> {
    /**
     * The dialog target entity, the entity that provides the dialog and represents a relative position.
     */
    private Entity targetEntity;
    /**
     * The dialog interacting entity.
     */
    private Entity interactingEntity = null;
    /**
     * The dialog position, relative to the target entity.
     */
    private DialogPosition position;

    /**
     * Creates a new instance of the Dialog class.
     * @param entity The dialog target entity.
     * @param position The dialog position, relative to the target entity.
     */
    public Dialog(Entity entity, DialogPosition position) {
        this.targetEntity = entity;
        this.position     = position;
    }

    /**
     * Gets the dialog interacting entity.
     * @return The dialog interacting entity.
     */
    public Entity getInteractingEntity() {
        return interactingEntity;
    }

    /**
     * Sets the dialog interacting entity.
     * @param interactingEntity The dialog interacting entity.
     */
    public void setInteractingEntity(Entity interactingEntity) {
        this.interactingEntity = interactingEntity;
    }

    /**
     * Render the dialog.
     * @param context The render context.
     */
    // TODO: This should also take params defining the position of the dialog to render.
    public abstract void render(TRenderContext context);
}
