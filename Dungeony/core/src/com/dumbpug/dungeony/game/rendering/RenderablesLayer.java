package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A layer of renderables.
 */
public class RenderablesLayer {
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
     * @param batch The sprite batch to use in rendering the renderables.
     */
    public void render(SpriteBatch batch) {
        // Sort the renderables based on their rendering order.
        Collections.sort(this.renderables, RenderablesLayer.renderOrderComparator);

        // Render each of the renderables.
        for (IRenderable renderable : this.renderables) {
            renderable.render(batch);
        }
    }
}
