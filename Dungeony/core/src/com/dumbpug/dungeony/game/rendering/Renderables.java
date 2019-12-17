package com.dumbpug.dungeony.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A list of renderables sorted by z-index.
 */
public class Renderables {
    /**
     * The underlying list of renderables.
     */
    private ArrayList<IRenderable> renderables = new ArrayList<IRenderable>();
    /**
     * The comparator to use in sorting the renderables.
     */
    private static Comparator<IRenderable> sortComparator;

    static {
        sortComparator = new Comparator<IRenderable>() {
            @Override
            public int compare(IRenderable first, IRenderable second) {
                float difference = first.getRenderOrder() - second.getRenderOrder();

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
        if (!this.renderables.contains(renderable)) {
            this.renderables.add(renderable);
        }
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
        if (this.renderables.contains(renderable)) {
            this.renderables.remove(renderable);
        }
    }

    /**
     * Sort and rendering the renderables.
     * @param batch The sprite batch to use in rendering the renderables.
     */
    public void render(SpriteBatch batch) {
        // Sort the renderables based on their rendering order.
        Collections.sort(this.renderables, Renderables.sortComparator);

        // Render each of the renderables.
        for (IRenderable renderable : this.renderables) {
            renderable.render(batch);
        }
    }
}
