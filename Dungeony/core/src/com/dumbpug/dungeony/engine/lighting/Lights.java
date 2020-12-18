package com.dumbpug.dungeony.engine.lighting;

import java.util.ArrayList;

/**
 * The collection of level lights.
 */
public class Lights<TRenderContext> {
    /**
     * The collection of lights.
     */
    private ArrayList<Light<TRenderContext>> lights = new ArrayList<Light<TRenderContext>>();

    /**
     *
     * @param light
     */
    public void add(Light light) {
        if (!this.lights.contains(light)) {
            this.lights.add(light);
        }
    }

    /**
     *
     * @param light
     */
    public void remove(Light light) {
        this.lights.remove(light);
    }

    /**
     *
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
