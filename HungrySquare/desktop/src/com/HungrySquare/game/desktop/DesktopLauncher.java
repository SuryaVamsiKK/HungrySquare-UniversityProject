package com.HungrySquare.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.HungrySquare.game.HungrySquare;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = HungrySquare.WIDTH;
		config.height = HungrySquare.HEIGHT;
		new LwjglApplication(new HungrySquare(), config);
	}
}
