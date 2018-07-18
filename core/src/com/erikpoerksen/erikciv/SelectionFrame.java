package com.erikpoerksen.erikciv;

import com.badlogic.gdx.graphics.Texture;
import com.erikpoerksen.erikciv.Adapter.CityTile;
import com.erikpoerksen.erikciv.Adapter.TerrainTile;
import com.erikpoerksen.erikciv.Adapter.UnitTile;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Game;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;

public class SelectionFrame {

    Position selectedPosition;
    Game game;

    public SelectionFrame(Game game){
        this.game = game;
        this.selectedPosition = null;
    }

    public void selectPosition(Position position){
        if(selectedPosition != null && selectedPosition.equals(position)){
            clearSelectedPosition();
            return;
        }
        selectedPosition = position;
    }

    public Position getSelectedPosition(){
        return selectedPosition;
    }

    public void clearSelectedPosition(){
        selectedPosition = null;
    }

    public Texture getTerrainTexture(){
        TerrainTypes type = game.getTerrainAtPosition(selectedPosition);
        return TerrainTile.convertTerrainTypeToTerrainTexture(type);
    }

    public Texture getCityTexture(){
        City city = game.getCityAtPosition(selectedPosition);
        return CityTile.convertCityToCityTexture(city);
    }

    public Texture getUnitTexture(){
        Unit unit = game.getUnitAtPosition(selectedPosition);
        return UnitTile.convertUnitToUnitTexture(unit);
    }


}
