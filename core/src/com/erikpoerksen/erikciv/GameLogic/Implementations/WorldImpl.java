package com.erikpoerksen.erikciv.GameLogic.Implementations;

import com.erikpoerksen.erikciv.GameLogic.Helpers.*;
import com.erikpoerksen.erikciv.GameLogic.Structure.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class WorldImpl implements World {

    public static final int rows = 10;
    public static final int columns = 10;
    private ArrayList<Player> players;

    Tile[][] world;

    public WorldImpl(String[][] worldString, ArrayList<Player> players){
        this.players = players;
        world = parseWorldString(worldString);
    }

    private Tile[][] parseWorldString(String[][] worldString){
        Tile[][] parsed = new Tile[rows][columns];
        for(int row = 0; row<rows; row++){

            String terrainLayer = worldString[0][row];
            String cityLayer = worldString[1][row];
            String unitLayer = worldString[2][row];

            String[] terrain = splitString(terrainLayer);
            String[] cities = splitString(cityLayer);
            String[] units = splitString(unitLayer);

            for(int column = 0; column<columns; column++){
                Tile tile = new Tile();

                tile.setTerrain(processTerrainLetters(terrain[column]));
                tile.setCity(processCityLetters(cities[column]));
                tile.setOccupyingUnit(processUnitLetters(units[column]));

                parsed[row][column] = tile;
            }
        }
        return parsed;
    }

    private String[] splitString(String string){
        return string.split(Pattern.quote("."));
    }

    private TerrainTypes processTerrainLetters(String letter){
        if(letter.equals("M")){
            return TerrainTypes.MOUNTAIN;
        }
        if(letter.equals("P")){
            return TerrainTypes.PLAINS;
        }
        if(letter.equals("F")){
            return TerrainTypes.FOREST;
        }
        if(letter.equals("H")){
            return TerrainTypes.HILLS;
        }
        if(letter.equals("O")){
            return TerrainTypes.OCEAN;
        }
        return null; // shouldn't happen, string should be validated much earlier
    }

    private Player getPlayerByColor(PlayerTypes type){
        for(Player player : players){
            if(player.getColor() == type){
                return player;
            }
        }
        return null;
    }

    private City processCityLetters(String letters){
        if(letters.equals("-")){
            return null;
        }

        String color = letters.substring(0,1);
        Player owner = null;

        if(color.equals("B")){
            owner = getPlayerByColor(PlayerTypes.BLUE);
        } else if(color.equals("R")){
            owner = getPlayerByColor(PlayerTypes.RED);
        }

        String type = letters.substring(1, 2);

        if(type.equals("C")){
            return new CityImpl(owner);
        }

        return null; // shouldn't happen, string should be validated much earlier
    }

    private Unit processUnitLetters(String letters){
        if(letters.equals("-")){
            return null;
        }

        String color = letters.substring(0,1);
        Player owner = null;

        if(color.equals("B")){
            owner = getPlayerByColor(PlayerTypes.BLUE);
        } else if(color.equals("R")){
            owner = getPlayerByColor(PlayerTypes.RED);
        }

        String type = letters.substring(1, 2);

        if(type.equals("A")){
            return new UnitImpl(owner, UnitTypes.ARCHER);
        }
        if(type.equals("L")){
            return new UnitImpl(owner, UnitTypes.LEGION);
        }
        if(type.equals("W")){
            return new UnitImpl(owner, UnitTypes.WOKER);
        }

        return null; // shouldn't happen, string should be validated much earlier
    }

    @Override
    public Unit getUnitAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return world[x][y].getOccupyingUnit();
    }

    @Override
    public TerrainTypes getTerrainAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return world[x][y].getTerrain();
    }

    @Override
    public City getCityAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return world[x][y].getCity();
    }

    @Override
    public void moveUnit(Position from, Position to) {
        Unit toBeMoved = getUnitAtPosition(from);
        int distance = HelperMethods.calculateShortestDirectDistance(from, to);
        toBeMoved.move(distance);
        world[to.getX()][to.getY()].setOccupyingUnit(toBeMoved);
        world[from.getX()][from.getY()].setOccupyingUnit(null);
    }

    private class Tile {

        private TerrainTypes terrain;
        private Unit occupyingUnit;
        private City city;

        public void setTerrain(TerrainTypes contents){
            this.terrain = contents;
        }

        public void setOccupyingUnit(Unit contents){
            this.occupyingUnit = contents;
        }

        public void setCity(City contents){
            this.city = contents;
        }

        public TerrainTypes getTerrain(){
            return terrain;
        }

        public City getCity(){
            return city;
        }

        public Unit getOccupyingUnit(){
            return occupyingUnit;
        }



    }

}
