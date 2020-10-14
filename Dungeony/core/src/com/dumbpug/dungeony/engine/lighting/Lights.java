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
        this.lights.add(light);
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
            light.callRender(context);
        }
    }
}
