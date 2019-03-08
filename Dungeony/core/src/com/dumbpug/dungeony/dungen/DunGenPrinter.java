package com.dumbpug.dungeony.dungen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.dumbpug.dungeony.dungen.tile.Tile;

/**
 * Prints a dungeon to an image.
 */
public class DunGenPrinter {
	
	public static void print(String name, String path, Dungeon dungeon) {
		int size   = 400;
		int offset = size / 2;
		
		File outputfile   = new File(path + name + ".png");
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		// Generate each static tile and draw it to our image.
		for (Tile tile : dungeon.getTiles()) {
			drawTileToImage(img, tile.getX() + offset, tile.getY() + offset, false);
		}
		
		// Try to write the image to disk.
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void drawTileToImage(BufferedImage image, int x, int y, boolean isBlack) {
		int r = 0, g = 0, b = 0;
		
		if (isBlack) {
			r = 0;
			g = 0; 
			b = 0;
		} else {
			r = 255;
			g = 255; 
			b = 255;
		}
		
		// Create the colour.
		int colour = (r << 16) | (g << 8) | b;
		
		// Set the pixel colour at the x/y position.
		image.setRGB(x, y, colour);
	}
}
