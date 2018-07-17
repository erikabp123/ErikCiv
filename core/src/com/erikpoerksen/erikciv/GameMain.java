package com.erikpoerksen.erikciv;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erikpoerksen.erikciv.Adapter.CityTile;
import com.erikpoerksen.erikciv.Adapter.TerrainTile;
import com.erikpoerksen.erikciv.Adapter.TileMap;
import com.erikpoerksen.erikciv.Adapter.UnitTile;
import com.erikpoerksen.erikciv.GameLogic.Implementations.GameImpl;

import java.util.ArrayList;

public class GameMain extends Game {
	
	@Override
	public void create () {
	    this.setScreen(new MainGameScreen(this));
	}

	@Override
	public void render () {
	    super.render();
	}
	
	@Override
	public void dispose () {

	}
}
