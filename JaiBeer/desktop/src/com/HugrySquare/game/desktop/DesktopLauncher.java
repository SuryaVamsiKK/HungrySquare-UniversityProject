package com.HugrySquare.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.HugrySquare.game.HugrySquare;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = HugrySquare.WIDTH;
		config.height = HugrySquare.HEIGHT;
		new LwjglApplication(new HugrySquare(), config);
	}
}
