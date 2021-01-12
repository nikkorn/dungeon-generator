package com.dumbpug.dungeony.utilities.shaders;

/**
 * Represents a game shader.
 */
public interface IShader {
    /**
     * Gets the vertex shader.
     * @return The vertex shader.
     */
    String getVertexShader();

    /**
     * Gets the fragment shader.
     * @return The fragment shader.
     */
    String getFragmentShader();
}
