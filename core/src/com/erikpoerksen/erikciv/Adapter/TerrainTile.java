package com.erikpoerksen.erikciv.Adapter;

import com.badlogic.gdx.graphics.Texture;
import com.erikpoerksen.erikciv.GameLogic.Helpers.GameConstants;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;

public class TerrainTile {

    Texture texture;
    int xCord;
    int yCord;

    public TerrainTile(TerrainTypes type, Position gamePosition){
        this.texture = convertTerrainTypeToTerrainTexture(type);
        createAndSetCords(gamePosition);
    }

    public static Texture convertTerrainTypeToTerrainTexture(TerrainTypes type){
        String fileName = "";
        String directory = "Terrain/";
        if(type == TerrainTypes.MOUNTAIN){
            fileName = "Mountain.png";
        } else if(type == TerrainTypes.OCEAN){
            fileName = "Ocean.png";
        } else if(type == TerrainTypes.FOREST){
            fileName = "Forest.png";
        } else if(type == TerrainTypes.HILLS){
            fileName = "Hills.png";
        } else if(type == TerrainTypes.PLAINS){
            fileName = "Plains.png";
        }
        return new Texture(directory + fileName);
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
