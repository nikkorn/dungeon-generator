package com.dumbpug.dungen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumbpug.dungen.DunGen;
import com.dumbpug.dungen.ToolPanel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new ToolPanel();
		// new LwjglApplication(new DunGen(), config);
	}
}
