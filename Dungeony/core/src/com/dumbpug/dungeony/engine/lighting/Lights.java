package com.dumbpug.dungeony.engine.lighting;

import java.util.ArrayList;

/**
 * The collection of environment lights.
 */
public class Lights<TRenderContext> {
    /**
     * The collection of lights.
     */
    private ArrayList<Light<TRenderContext>> lights = new ArrayList<Light<TRenderContext>>();

    /**
     * Add a light to the collection of environment lights.
     * @param light The light to add.
     */
    public void add(Light light) {
        if (!this.lights.contains(light)) {
            this.lights.add(light);
        }
    }

    /**
     * Remove a light from the collection of environment lights.
     * @param light The light to remove.
     */
    public void remove(Light light) {
        this.lights.remove(light);
    }

    /**
     * Render the collection of environment lights.
     * @param context The render context
     */
    public void render(TRenderContext context) {
        for (Light light : this.lights) {
            // Skip rendering a light it if is not enabled.
            if (!light.isEnabled()) {
                continue;
            }

            // Render the light.
            light.callRender(context);
        }
    }
}
