package com.dumbpug.dungeony.engine.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A layer of renderables.
 * @param <TRenderContext> The render context.
 */
public class RenderablesLayer<TRenderContext> {
    /**
     * The layer depth.
     */
    private int depth;
    /**
     * The list of renderables at this layer.
     */
    private ArrayList<IRenderable> renderables = new ArrayList<IRenderable>();
    /**
     * The comparator to use in sorting the renderables by order.
     */
    private static Comparator<IRenderable> renderOrderComparator;

    static {
        renderOrderComparator = new Comparator<IRenderable>() {
            @Override
            public int compare(IRenderable first, IRenderable second) {
                float difference = first.getRenderOrder() - second.getRenderOrder();

                if (difference < 0) {
                    return 1;
                } else if (difference > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
    }

    /**
     * Creates a new instance of the RenderablesLayer class.
     * @param depth The layer depth.
     */
    public RenderablesLayer(int depth) {
        this.depth = depth;
    }

    /**
     * Get the layer depth.
     * @return The layer depth.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Add the specified renderable.
     * @param renderable The renderable to add.
     */
    public void add(IRenderable renderable) {
        // Get the layer at which to add the renderable.
        if (!this.renderables.contains(renderable)) {
            this.renderables.add(renderable);
        }
    }

    /**
     * Remove the specified renderable.
     * @param renderable The renderable to remove.
     */
    public void remove(IRenderable renderable) {
        if (this.renderables.contains(renderable)) {
            this.renderables.remove(renderable);
        }
    }

    /**
     * Sort and render the renderables.
     * @param context The render context
     * @param window The render window.
     */
    public void render(TRenderContext context, IRenderWindow window) {
        // Sort the renderables based on their rendering order.
        Collections.sort(this.renderables, RenderablesLayer.renderOrderComparator);

        // Render each of the renderables.
        for (IRenderable renderable : this.renderables) {
            // Ignore any renderable not within the current render window if one is defined.
            if (window != null && !window.contains(renderable)) {
                continue;
            }

            renderable.render(context);
        }
    }
}
