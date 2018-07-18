package com.erikpoerksen.erikciv.Adapter;

import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Game;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;
import com.erikpoerksen.erikciv.GraphicsConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class TileMap {

    Game game;

    ArrayList<TerrainTile> terrainLayout;
    ArrayList<CityTile> cityLayout;
    ArrayList<UnitTile> unitLayout;

    public TileMap(Game game){
        this.game = game;
        this.terrainLayout = new ArrayList<>();
        this.cityLayout = new ArrayList<>();
        this.unitLayout = new ArrayList<>();
        updateTerrainLayout();
        updateCityLayout();
        updateUnitLayout();
    }

    public ArrayList<TerrainTile> getTerrainLayout(){
        return terrainLayout;
    }

    public void updateTerrainLayout(){
        terrainLayout.clear();
        for(int row=0; row<GameConstants.X_LENGTH; row++){
            for(int column=0; column<GameConstants.Y_LENGTH; column++){
                Position position = new Position(row, column);
                TerrainTypes type = game.getTerrainAtPosition(position);
                TerrainTile tile = new TerrainTile(type, position);
                terrainLayout.add(tile);
            }
        }
    }

    public ArrayList<CityTile> getCityLayout(){
        return cityLayout;
    }

    public void updateCityLayout(){
        cityLayout.clear();
        for(int row=0; row<GameConstants.X_LENGTH; row++){
            for(int column=0; column<GameConstants.Y_LENGTH; column++){
                Position position = new Position(row, column);
                City city = game.getCityAtPosition(position);
                if(city == null){
                    continue;
                }
                CityTile tile = new CityTile(city, position);
                cityLayout.add(tile);
            }
        }
    }

    public ArrayList<UnitTile> getUnitLayout(){
        return unitLayout;
    }

    public void updateUnitLayout(){
        unitLayout.clear();
        for(int row=0; row<GameConstants.X_LENGTH; row++){
            for(int column=0; column<GameConstants.Y_LENGTH; column++){
                Position position = new Position(row, column);
                Unit unit = game.getUnitAtPosition(position);
                if(unit == null){
                    continue;
                }
                UnitTile tile = new UnitTile(unit, position);
                unitLayout.add(tile);
            }
        }
    }

    public static Position convertPosition(Position position){
        // need to adapt my weird coordinate set where y GROWS downwards to a normal x-y
        // To convert:
        // Flip x (0 becomes n, where n is the largest x coordinate)
        // Swap x and y around
        int xFlipped = (GameConstants.X_LENGTH-1) - position.getX();
        int yOffset = GraphicsConstants.UI_SELECTION_FRAME_HEIGHT;
        int xCord = position.getY()*GraphicsConstants.TILE_SIZE;
        int yCord = xFlipped*GraphicsConstants.TILE_SIZE + yOffset;
        return new Position(xCord, yCord);
    }





}
