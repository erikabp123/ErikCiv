package com.erikpoerksen.erikciv.Adapter;

import com.badlogic.gdx.graphics.Texture;
import com.erikpoerksen.erikciv.GameLogic.Helpers.Position;
import com.erikpoerksen.erikciv.GameLogic.Helpers.TerrainTypes;
import com.erikpoerksen.erikciv.GameLogic.Helpers.UnitTypes;
import com.erikpoerksen.erikciv.GameLogic.Structure.Unit;

public class UnitTile {

    Texture texture;
    int xCord;
    int yCord;

    public UnitTile(Unit unit, Position gamePosition){
        this.texture = convertUnitToUnitTexture(unit);
        createAndSetCords(gamePosition);
    }

    public static Texture convertUnitToUnitTexture(Unit unit){
        String fileName = "";
        String directory = "Units/";
        UnitTypes type = unit.getType();
        if(type == UnitTypes.LEGION){
            fileName = "Legion.png";
        } else if(type == UnitTypes.ARCHER){
            fileName = "Archer.png";
        } else if(type == UnitTypes.WORKER){
            fileName = "Worker.png";
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
