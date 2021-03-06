package com.dumbpug.dungeony.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.dungeony.Dungeony;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.backgroundFPS = 1000;
		config.foregroundFPS = 1000;
		config.vSyncEnabled  = false;
		// config.fullscreen    = true;
		new LwjglApplication(new Dungeony(), config);
	}
}
