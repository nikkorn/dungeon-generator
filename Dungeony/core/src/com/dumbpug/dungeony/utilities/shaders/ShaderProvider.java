package com.dumbpug.dungeony.utilities.shaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import java.util.HashMap;

/**
 * Provider of SpriteBatch shaders.
 */
public class ShaderProvider {
    /**
     * The default shader.
     */
    private static ShaderProgram defaultShader;
    /**
     * The non-default shaders.
     */
    private static HashMap<ShaderType, ShaderProgram> shaders;

    static {
        ShaderProgram.pedantic = false;
        defaultShader = SpriteBatch.createDefaultShader();
        shaders = new HashMap<ShaderType, ShaderProgram>() {{
            // Add the solid white shader.
            SolidWhiteShader solidWhiteShader = new SolidWhiteShader();
            put(ShaderType.SOLID_WHITE, new ShaderProgram(solidWhiteShader.getVertexShader(), solidWhiteShader.getFragmentShader()));
        }};
    }

    /**
     * Gets the shader of the specified type.
     * @param type The shader type.
     * @return The shader of the specified type.
     */
    public static ShaderProgram getShader(ShaderType type) {
        return shaders.get(type);
    }

    /**
     * Gets the default application shader.
     * @return The default application shader.
     */
    public static ShaderProgram getDefault() {
        return defaultShader;
    }
}
