package com.dumbpug.dungeony.engine.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A collection of renderables sorted by layer depth and then by individual order.
 * @param <TRenderContext> The render context.
 */
public class Renderables<TRenderContext> {
    /**
     * The list of renderable layers.
     */
    private ArrayList<RenderablesLayer> layers = new ArrayList<RenderablesLayer>();
    /**
     * The comparator to use in sorting the renderable layers by order.
     */
    private static Comparator<RenderablesLayer> layerOrderComparator;

    static {
        layerOrderComparator = new Comparator<RenderablesLayer>() {
            @Override
            public int compare(RenderablesLayer first, RenderablesLayer second) {
                int difference = first.getDepth() - second.getDepth();

                if (difference < 0) {
                    return -1;
                } else if (difference > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }

    /**
     * Add the specified renderable.
     * @param renderable The renderable to add.
     */
    public void add(IRenderable renderable) {
        // Get the layer at which we will add the renderable and add it.
        getLayer(renderable.getRenderLayer()).add(renderable);
    }

    /**
     * Add the list of specified renderables.
     * @param list The list of renderables to add.
     */
    public void add(ArrayList<? extends IRenderable> list) {
        for (IRenderable renderable : list) {
            this.add(renderable);
        }
    }

    /**
     * Remove the specified renderable.
     * @param renderable The renderable to remove.
     */
    public void remove(IRenderable renderable) {
        // Get the layer at which we will be removing the renderable and remove it.
        getLayer(renderable.getRenderLayer()).remove(renderable);
    }

    /**
     * Sort and render the renderable layers.
     * @param context The render context
     * @param window The render window.
     */
    public void render(TRenderContext context, IRenderWindow window) {
        // Sort the renderables based on their rendering order.
        Collections.sort(this.layers, Renderables.layerOrderComparator);

        // Render each of the renderables.
        for (RenderablesLayer layer : this.layers) {
            layer.render(context, window);
        }
    }

    /**
     * Gets the layer at the specified depth, or creates one if one does not already exist.
     * @param depth The layer depth.
     * @return The layer at the specified depth.
     */
    private RenderablesLayer getLayer(int depth) {
        // Try to get the layer with the specified depth if it already exists.
        for (RenderablesLayer layer : this.layers) {
            if (layer.getDepth() == depth) {
                return layer;
            }
        }

        // A layer with the specified depth does not exist so create it now.
        RenderablesLayer layer = new RenderablesLayer(depth);

        // Add the new layer to the list of existing layers.
        this.layers.add(layer);

        // Return the newly created layer.
        return layer;
    }
}
