package com.erikpoerksen.erikciv.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.erikpoerksen.erikciv.Adapter.TileMap;
import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameMain;
import com.erikpoerksen.erikciv.GraphicsConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConstants.X_LENGTH * GraphicsConstants.TILE_SIZE;
		config.height = GameConstants.Y_LENGTH * GraphicsConstants.TILE_SIZE + GraphicsConstants.UI_COMMAND_HEIGHT +GraphicsConstants.UI_SELECTION_FRAME_HEIGHT;
		new LwjglApplication(new GameMain(), config);
	}
}
