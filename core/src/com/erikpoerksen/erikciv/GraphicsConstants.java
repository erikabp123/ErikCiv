package com.erikpoerksen.erikciv;

import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;

public class GraphicsConstants {

    // GAME
    public static final int TILE_SIZE = 50;
    public static final int WORLD_HEIGHT = GameConstants.Y_LENGTH*TILE_SIZE;
    public static final int WORLD_WIDTH = GameConstants.X_LENGTH*TILE_SIZE;

    // UI
    public static final int UI_COMMAND_HEIGHT= 40;
    public static final int UI_SELECTION_FRAME_HEIGHT= 150;
    public static MouseMode MOUSEMODE = MouseMode.SELECT;



}
