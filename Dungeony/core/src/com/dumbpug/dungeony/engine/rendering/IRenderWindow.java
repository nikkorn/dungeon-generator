package com.dumbpug.dungeony.engine.rendering;

/**
 * Represents a window in which overlapping renderables should be rendered.
 */
public interface IRenderWindow {
    /**
     * Gets whether the renderable is contained within the render window and should be rendered.
     * @param renderable The renderable.
     * @return Whether the renderable is contained within the render window and should be rendered.
     */
    boolean contains(IRenderable renderable);
}
