package com.dumbpug.dungeony.engine.dialog;

import com.dumbpug.dungeony.engine.Entity;

import java.util.ArrayList;

/**
 * The collection of environment dialogs.
 */
public class Dialogs<TRenderContext> {
    /**
     * The collection of dialogs.
     */
    private ArrayList<Dialog<TRenderContext>> dialogs = new ArrayList<Dialog<TRenderContext>>();

    /**
     * Add a dialog to the collection of environment dialogs.
     * @param dialog The dialog to add.
     */
    public void add(Dialog dialog) {
        if (!this.dialogs.contains(dialog)) {
            this.dialogs.add(dialog);
        }
    }

    /**
     * Remove a dialog from the collection of environment dialogs.
     * @param dialog The dialog to remove.
     */
    public void remove(Dialog dialog) {
        this.dialogs.remove(dialog);
    }

    /**
     * Gets a list of active dialogs for the given entity.
     * @param entity The entity.
     * @return A list of active dialogs for the given entity.
     */
    public ArrayList<Dialog> getDialogsWithInteractingEntity(Entity entity) {
        ArrayList<Dialog> activeDialogs = new ArrayList<Dialog>();

        for (Dialog dialog : this.dialogs) {
            if (dialog.getInteractingEntity() == entity) {
                activeDialogs.add(dialog);
            }
        }

        return activeDialogs;
    }

    /**
     * Render the collection of environment dialogs.
     * @param context The render context
     */
    public void render(TRenderContext context) {
        for (Dialog dialog : this.dialogs) {
            // Skip rendering a dialog if it has no interacting entity.
            if (dialog.getInteractingEntity() == null) {
                continue;
            }

            // Render the dialog.
            dialog.render(context);
        }
    }
}
