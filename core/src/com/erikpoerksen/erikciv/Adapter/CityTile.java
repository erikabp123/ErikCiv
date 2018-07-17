package com.erikpoerksen.erikciv.Adapter;

import com.badlogic.gdx.graphics.Texture;
import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.City;
import com.erikpoerksen.erikciv.GameLogic.Structure.Player;

public class CityTile {

    Texture texture;
    int xCord;
    int yCord;

    public CityTile(City city, Position gamePosition){
        String fileName = "";
        String directory = "Cities/";
        fileName = "City.png";
        this.texture = new Texture(directory + fileName);
        createAndSetCords(gamePosition);
    }

    private void createAndSetCords(Position position){
        position = TileMap.convertPosition(position);
        xCord = position.getX();
        yCord = position.getY();
    }

    public int getxCord(){
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public Texture getTexture(){
        return texture;
    }




}
