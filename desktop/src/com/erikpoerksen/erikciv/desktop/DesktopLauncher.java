package com.erikpoerksen.erikciv.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.erikpoerksen.erikciv.Adapter.TileMap;
import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConstants.X_LENGTH * TileMap.TILE_SIZE;
		config.height = GameConstants.Y_LENGTH * TileMap.TILE_SIZE + 40;
		new LwjglApplication(new GameMain(), config);
	}
}
