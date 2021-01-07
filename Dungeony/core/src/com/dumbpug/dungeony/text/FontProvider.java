package com.dumbpug.dungeony.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.dumbpug.dungeony.Constants;
import java.util.HashMap;

/**
 * Provider of game fonts.
 */
public class FontProvider {
    /**
     * Mappings of font types to generators.
     */
    private static HashMap<FontType, FreeTypeFontGenerator> fontGenerators = new HashMap<FontType, FreeTypeFontGenerator>();

    static {
        fontGenerators.put(FontType.MAIN_FONT, new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + Constants.FONT_TYPE_MAIN)));
    }

    /**
     * Get a game font.
     * @param type The font type.
     * @param size The font size.
     * @return The game font
     */
    public static BitmapFont getFont(FontType type, FontSize size) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        switch (size) {
            case SMALL:
                parameter.size = Constants.FONT_SIZE_SMALL;
                break;
            case MEDIUM:
                parameter.size = Constants.FONT_SIZE_MEDIUM;
                break;
            case LARGE:
                parameter.size = Constants.FONT_SIZE_LARGE;
                break;
        }

        return fontGenerators.get(type).generateFont(parameter);
    }
}
